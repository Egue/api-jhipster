//! Interfaz de escritorio para printbridge (solo Windows):
//! icono en la bandeja del sistema + panel de logs.
//!
//! - El panel de logs se muestra al arrancar.
//! - Al minimizar (o cerrar con la X) se oculta pero sigue corriendo;
//!   se vuelve a mostrar con doble clic en el icono de bandeja o desde su menú.
//! - En release (`cargo build --release`) la ventana negra de consola no
//!   aparece (subsistema de Windows); en debug sigue habiendo consola.

use std::collections::VecDeque;
use std::sync::atomic::{AtomicBool, Ordering};
use std::sync::{LazyLock, Mutex};

const MAX_CHARS: usize = 30_000;

static LOG_TEXT: LazyLock<Mutex<String>> = LazyLock::new(|| Mutex::new(String::new()));
static PENDING: LazyLock<Mutex<VecDeque<String>>> = LazyLock::new(|| Mutex::new(VecDeque::new()));
static DIRTY: AtomicBool = AtomicBool::new(false);

/// Registra una línea para el panel de logs (y a stderr solo en debug).
pub fn log(msg: &str) {
    if let Ok(mut q) = PENDING.lock() {
        q.push_back(msg.to_string());
        while q.len() > 200 {
            q.pop_front();
        }
    }
    DIRTY.store(true, Ordering::SeqCst);
    #[cfg(debug_assertions)]
    eprintln!("{msg}");
}

/// Arranca la interfaz (no-op fuera de Windows).
#[cfg(not(windows))]
pub fn run_ui() {}

#[cfg(windows)]
pub fn run_ui() {
    std::thread::Builder::new()
        .name("HJPrints-ui".into())
        .spawn(imp::ui_main)
        .expect("No se pudo crear el hilo de UI");
}

#[cfg(windows)]
mod imp {
    #![allow(non_snake_case, non_upper_case_globals, dead_code)]

    use super::{log, DIRTY, PENDING};
    use std::ffi::c_void;
    use std::mem::zeroed;
    use std::os::windows::process::CommandExt;
    use std::ptr;
    use std::sync::atomic::{AtomicUsize, Ordering};
    use windows_sys::Win32::Foundation::{HWND, LPARAM, LRESULT, RECT, TRUE, WPARAM};
    use windows_sys::Win32::Graphics::Gdi::{
        CreateDIBSection, DeleteObject, BITMAPINFO, BITMAPINFOHEADER, BI_RGB, DIB_RGB_COLORS,
    };
    use windows_sys::Win32::System::LibraryLoader::GetModuleHandleW;
    use windows_sys::Win32::UI::Shell::{
        Shell_NotifyIconW, NIF_ICON, NIF_MESSAGE, NIF_TIP, NIM_ADD, NIM_DELETE, NOTIFYICONDATAW,
        NOTIFYICONDATAW_0,
    };
    use windows_sys::Win32::UI::WindowsAndMessaging::{
        AppendMenuW, CheckMenuItem, CreateIconIndirect, CreatePopupMenu, CreateWindowExW,
        DefWindowProcW, DestroyMenu, DispatchMessageW, GetClientRect, GetCursorPos, GetMessageW,
        IMAGE_ICON, KillTimer, LoadImageW, LR_DEFAULTCOLOR, MoveWindow, PostQuitMessage,
        RegisterClassW, SendMessageW, SetForegroundWindow, SetTimer, ShowWindow, TrackPopupMenu,
        TranslateMessage, HMENU, MF_STRING, MSG, SW_HIDE, SW_SHOW, TPM_BOTTOMALIGN,
        TPM_RIGHTBUTTON, WS_EX_CLIENTEDGE, WS_OVERLAPPEDWINDOW, WS_VISIBLE, WS_VSCROLL,
        WNDCLASSW, ICONINFO,
    };

    // Constantes de mensajes/estilos que windows-sys agrupa por tipo; se definen
    // localmente para no depender del feature de controles.
    const TRAY_MSG: u32 = 0x8000 + 1;
    const WM_COMMAND: u32 = 0x0111;
    const WM_DESTROY: u32 = 0x0002;
    const WM_SETTEXT: u32 = 0x000C;
    const WM_SIZE: u32 = 0x0005;
    const WM_SETICON: u32 = 0x0080;
    const WM_SYSCOMMAND: u32 = 0x0112;
    const WM_CLOSE: u32 = 0x0010;
    const WM_TIMER: u32 = 0x0113;
    const WM_LBUTTONUP: u32 = 0x0202;
    const WM_LBUTTONDBLCLK: u32 = 0x0203;
    const WM_RBUTTONUP: u32 = 0x0205;
    const SC_MINIMIZE: usize = 0xF020;
    const ICON_SMALL: usize = 0;
    const ICON_BIG: usize = 1;
    const EM_SETSEL: u32 = 0x00B1;
    const EM_SCROLLCARET: u32 = 0x00B7;
    const WS_CHILD: u32 = 0x40000000;
    const WS_HSCROLL: u32 = 0x00100000;
    const ES_MULTILINE: u32 = 0x0004;
    const ES_READONLY: u32 = 0x0800;
    const ES_AUTOVSCROLL: u32 = 0x0040;
    const NID_ID: u32 = 1;
    const TIMER_LOGS: usize = 1;
    const MENU_SHOW: usize = 1;
    const MENU_EXIT: usize = 2;
    const MENU_STARTUP: usize = 3;
    const MF_CHECKED: u32 = 0x00000008;
    const MF_UNCHECKED: u32 = 0x00000000;
    const CREATE_NO_WINDOW: u32 = 0x0800_0000;

    static TRAY_HWND: AtomicUsize = AtomicUsize::new(0);
    static LOG_HWND: AtomicUsize = AtomicUsize::new(0);
    static EDIT_HWND: AtomicUsize = AtomicUsize::new(0);
    static TRAY_ICON: AtomicUsize = AtomicUsize::new(0);

    fn wide(s: &str) -> Vec<u16> {
        s.encode_utf16().chain(std::iter::once(0)).collect()
    }

    fn hw(v: usize) -> HWND {
        v as *mut c_void
    }

    // El parámetro hMenu de CreateWindowExW sirve como ID de control hijo.
    fn child_id(id: usize) -> HMENU {
        id as *mut c_void
    }

    pub fn ui_main() {
        let hinst = unsafe { GetModuleHandleW(ptr::null()) };
        let class_tray = wide("printbridge_tray");
        let class_logs = wide("printbridge_logs");

        let mut wc1 = unsafe { zeroed::<WNDCLASSW>() };
        wc1.lpfnWndProc = Some(tray_proc);
        wc1.hInstance = hinst;
        wc1.lpszClassName = class_tray.as_ptr();
        if unsafe { RegisterClassW(&wc1) } == 0 {
            log("AVISO: no se pudo registrar la clase de bandeja");
        }
        let mut wc2 = unsafe { zeroed::<WNDCLASSW>() };
        wc2.lpfnWndProc = Some(log_proc);
        wc2.hInstance = hinst;
        wc2.lpszClassName = class_logs.as_ptr();
        if unsafe { RegisterClassW(&wc2) } == 0 {
            log("AVISO: no se pudo registrar la clase de logs");
        }

        let hicon = load_icon(32);
        TRAY_ICON.store(hicon as usize, Ordering::SeqCst);

        let title_tray = wide("printbridge");
        let tray_hwnd = unsafe {
            CreateWindowExW(
                0,
                class_tray.as_ptr(),
                title_tray.as_ptr(),
                0,
                0,
                0,
                0,
                0,
                ptr::null_mut(),
                ptr::null_mut(),
                hinst,
                ptr::null(),
            )
        };
        TRAY_HWND.store(tray_hwnd as usize, Ordering::SeqCst);

        let nid = tray_nid(tray_hwnd, hicon);
        unsafe { Shell_NotifyIconW(NIM_ADD, &nid) };

        let title_logs = wide("HJPrints — logs");
        let logwin = unsafe {
            CreateWindowExW(
                0,
                class_logs.as_ptr(),
                title_logs.as_ptr(),
                WS_OVERLAPPEDWINDOW | WS_VISIBLE,
                40,
                40,
                560,
                380,
                ptr::null_mut(),
                ptr::null_mut(),
                hinst,
                ptr::null(),
            )
        };
        let class_edit = wide("EDIT");
        let edit = unsafe {
            CreateWindowExW(
                WS_EX_CLIENTEDGE,
                class_edit.as_ptr(),
                ptr::null(),
                WS_CHILD
                    | WS_VISIBLE
                    | WS_VSCROLL
                    | WS_HSCROLL
                    | ES_MULTILINE
                    | ES_READONLY
                    | ES_AUTOVSCROLL,
                0,
                0,
                100,
                100,
                logwin,
                child_id(1),
                hinst,
                ptr::null(),
            )
        };
        LOG_HWND.store(logwin as usize, Ordering::SeqCst);
        EDIT_HWND.store(edit as usize, Ordering::SeqCst);

        // El WM_SIZE que manda CreateWindowExW ocurre antes de crear el EDIT
        // (y de tenerlo registrado), así que inicializamos el tamaño a mano.
        unsafe {
            let mut rc = zeroed::<RECT>();
            GetClientRect(logwin, &mut rc);
            MoveWindow(
                edit,
                0,
                0,
                rc.right - rc.left,
                rc.bottom - rc.top,
                TRUE,
            );
        }

        unsafe {
            SendMessageW(logwin, WM_SETICON, ICON_SMALL, hicon as isize);
            SendMessageW(logwin, WM_SETICON, ICON_BIG, hicon as isize);
            SetTimer(logwin, TIMER_LOGS, 400, None);
        }

        flush_logs();

        let mut msg = unsafe { zeroed::<MSG>() };
        while unsafe { GetMessageW(&mut msg, ptr::null_mut(), 0, 0) } > 0 {
            unsafe {
                TranslateMessage(&msg);
                DispatchMessageW(&msg);
            }
        }
    }

    fn tray_nid(hwnd: HWND, hicon: HWND) -> NOTIFYICONDATAW {
        let mut nid: NOTIFYICONDATAW = unsafe { zeroed() };
        nid.cbSize = std::mem::size_of::<NOTIFYICONDATAW>() as u32;
        nid.hWnd = hwnd;
        nid.uID = NID_ID;
        nid.uFlags = NIF_MESSAGE | NIF_ICON | NIF_TIP;
        nid.uCallbackMessage = TRAY_MSG;
        nid.hIcon = hicon;
        let tip = wide("HJPrints");
        for (i, c) in tip.iter().take(127).enumerate() {
            nid.szTip[i] = *c;
        }
        nid.szTip[127] = 0;
        nid.Anonymous = NOTIFYICONDATAW_0 { uTimeout: 3000 };
        nid
    }

    fn show_logs() {
        let h = LOG_HWND.load(Ordering::SeqCst);
        if h == 0 {
            return;
        }
        unsafe {
            ShowWindow(hw(h), SW_SHOW);
            SetForegroundWindow(hw(h));
        }
    }

    fn quit() {
        let nid = tray_nid(
            hw(TRAY_HWND.load(Ordering::SeqCst)),
            TRAY_ICON.load(Ordering::SeqCst) as HWND,
        );
        unsafe {
            Shell_NotifyIconW(NIM_DELETE, &nid);
            ShowWindow(hw(LOG_HWND.load(Ordering::SeqCst)), SW_HIDE);
            std::process::exit(0);
        }
    }

    const REG_RUN_KEY: &str = r"HKCU\Software\Microsoft\Windows\CurrentVersion\Run";

    fn is_startup_enabled() -> bool {
        std::process::Command::new("reg")
            .creation_flags(CREATE_NO_WINDOW)
            .args(["query", REG_RUN_KEY, "/v", "printbridge"])
            .output()
            .map(|o| o.status.success())
            .unwrap_or(false)
    }

    fn startup_full_cmd() -> String {
        let exe = std::env::current_exe()
            .map(|p| p.to_string_lossy().to_string())
            .unwrap_or_default();
        let args: Vec<String> = std::env::args().collect();
        if args.len() <= 1 {
            format!("\"{exe}\"")
        } else {
            format!("\"{exe}\" {}", args[1..].join(" "))
        }
    }

    fn toggle_startup() {
        if is_startup_enabled() {
            let _ = std::process::Command::new("reg")
                .creation_flags(CREATE_NO_WINDOW)
                .args([
                    "delete", REG_RUN_KEY, "/v", "printbridge", "/f",
                ])
                .status();
            log("Inicio automático desactivado");
        } else {
            let cmd = startup_full_cmd();
            let _ = std::process::Command::new("reg")
                .creation_flags(CREATE_NO_WINDOW)
                .args([
                    "add", REG_RUN_KEY, "/v", "printbridge", "/t", "REG_SZ", "/d", &cmd, "/f",
                ])
                .status();
            log(&format!("Inicio automático activado: {cmd}"));
        }
    }

    fn popup_tray_menu(hwnd: HWND) {
        let menu = unsafe { CreatePopupMenu() };
        let t_show = wide("&Mostrar panel de logs");
        let t_startup = wide("Ejecutar al &inicio");
        let t_exit = wide("&Salir");
        unsafe {
            AppendMenuW(menu, MF_STRING, MENU_SHOW, t_show.as_ptr());
            AppendMenuW(menu, MF_STRING, MENU_STARTUP, t_startup.as_ptr());
            let flag = if is_startup_enabled() { MF_CHECKED } else { MF_UNCHECKED };
            CheckMenuItem(menu, MENU_STARTUP as u32, flag);
            AppendMenuW(menu, MF_STRING, MENU_EXIT, t_exit.as_ptr());
            let mut pt = zeroed::<windows_sys::Win32::Foundation::POINT>();
            GetCursorPos(&mut pt);
            TrackPopupMenu(
                menu,
                TPM_RIGHTBUTTON | TPM_BOTTOMALIGN,
                pt.x,
                pt.y,
                0,
                hwnd,
                ptr::null(),
            );
            DestroyMenu(menu);
        }
    }

    fn flush_logs() {
        let edit = EDIT_HWND.load(Ordering::SeqCst);
        if edit == 0 {
            return;
        }
        let shared = {
            let mut t = super::LOG_TEXT.lock().unwrap();
            if let Ok(mut q) = PENDING.lock() {
                while let Some(line) = q.pop_front() {
                    t.push_str(&line);
                    t.push_str("\r\n");
                }
            }
            // Recortar si pasa de MAX_CHARS
            if t.len() > super::MAX_CHARS {
                if let Some(pos) = t[super::MAX_CHARS / 2..].find("\r\n") {
                    let cut = super::MAX_CHARS / 2 + pos + 2;
                    t.replace_range(..cut, "");
                }
            }
            t.clone()
        };
        let w = wide(&shared);
        unsafe {
            SendMessageW(hw(edit), WM_SETTEXT, 0, w.as_ptr() as isize);
            SendMessageW(hw(edit), EM_SETSEL, usize::MAX, isize::MAX);
            SendMessageW(hw(edit), EM_SCROLLCARET, 0, 0);
        }
    }

    unsafe extern "system" fn tray_proc(
        hwnd: HWND,
        msg: u32,
        wparam: WPARAM,
        lparam: LPARAM,
    ) -> LRESULT {
        match msg {
            TRAY_MSG => {
                let ev = (lparam as u32) & 0xFFFF;
                match ev {
                    WM_RBUTTONUP => popup_tray_menu(hwnd),
                    WM_LBUTTONUP | WM_LBUTTONDBLCLK => show_logs(),
                    _ => {}
                }
                0
            }
            WM_COMMAND => {
                match wparam & 0xFFFF {
                    MENU_SHOW => show_logs(),
                    MENU_STARTUP => toggle_startup(),
                    MENU_EXIT => quit(),
                    _ => {}
                }
                0
            }
            WM_DESTROY => {
                PostQuitMessage(0);
                0
            }
            _ => DefWindowProcW(hwnd, msg, wparam, lparam),
        }
    }

    unsafe extern "system" fn log_proc(
        hwnd: HWND,
        msg: u32,
        wparam: WPARAM,
        lparam: LPARAM,
    ) -> LRESULT {
        match msg {
            WM_SIZE => {
                let mut rc = unsafe { zeroed::<RECT>() };
                GetClientRect(hwnd, &mut rc);
                MoveWindow(
                    hw(EDIT_HWND.load(Ordering::SeqCst)),
                    0,
                    0,
                    rc.right - rc.left,
                    rc.bottom - rc.top,
                    TRUE,
                );
                0
            }
            WM_TIMER => {
                if wparam == TIMER_LOGS && DIRTY.swap(false, Ordering::SeqCst) {
                    flush_logs();
                }
                0
            }
            WM_SYSCOMMAND => {
                if (wparam & 0xFFF0) == SC_MINIMIZE {
                    ShowWindow(hwnd, SW_HIDE);
                    0
                } else {
                    DefWindowProcW(hwnd, msg, wparam, lparam)
                }
            }
            WM_CLOSE => {
                ShowWindow(hwnd, SW_HIDE);
                0
            }
            WM_DESTROY => {
                KillTimer(hwnd, TIMER_LOGS);
                0
            }
            _ => DefWindowProcW(hwnd, msg, wparam, lparam),
        }
    }

    // Icono para bandeja y ventana: usa el .ico embebido en el exe (recurso
    // "appicon", ver assets/icon.rc) si existe; si no, cae al dibujado.
    fn load_icon(size: i32) -> HWND {
        let hinst = unsafe { GetModuleHandleW(ptr::null()) };
        let name = wide("appicon");
        let h = unsafe {
            LoadImageW(hinst, name.as_ptr(), IMAGE_ICON, size, size, LR_DEFAULTCOLOR)
        } as HWND;
        if !h.is_null() {
            return h;
        }
        make_icon(size, size)
    }

    fn make_icon(w: i32, h: i32) -> HWND {
        let mut bi: BITMAPINFO = unsafe { zeroed() };
        bi.bmiHeader.biSize = std::mem::size_of::<BITMAPINFOHEADER>() as u32;
        bi.bmiHeader.biWidth = w;
        bi.bmiHeader.biHeight = -h; // top-down
        bi.bmiHeader.biPlanes = 1;
        bi.bmiHeader.biBitCount = 32;
        bi.bmiHeader.biCompression = BI_RGB;

        let mut bits: *mut c_void = ptr::null_mut();
        let color = unsafe {
            CreateDIBSection(
                ptr::null_mut(),
                &bi,
                DIB_RGB_COLORS,
                &mut bits,
                ptr::null_mut(),
                0,
            )
        };
        if bits.is_null() {
            return ptr::null_mut();
        }
        draw_icon(bits as *mut u8, w, h);

        let mut mbi: BITMAPINFO = unsafe { zeroed() };
        mbi.bmiHeader.biSize = std::mem::size_of::<BITMAPINFOHEADER>() as u32;
        mbi.bmiHeader.biWidth = w;
        mbi.bmiHeader.biHeight = -h;
        mbi.bmiHeader.biPlanes = 1;
        mbi.bmiHeader.biBitCount = 1;
        mbi.bmiHeader.biCompression = BI_RGB;
        let mut mb: *mut c_void = ptr::null_mut();
        let mask = unsafe {
            CreateDIBSection(
                ptr::null_mut(),
                &mbi,
                DIB_RGB_COLORS,
                &mut mb,
                ptr::null_mut(),
                0,
            )
        };
        let stride = ((w + 31) / 32) * 4;
        unsafe { ptr::write_bytes(mb, 0, stride as usize * h as usize) };

        let mut ii: ICONINFO = unsafe { zeroed() };
        ii.fIcon = TRUE;
        ii.hbmColor = color;
        ii.hbmMask = mask;
        let hicon = unsafe { CreateIconIndirect(&ii) };
        unsafe {
            DeleteObject(color);
            DeleteObject(mask);
        }
        hicon
    }

    fn set_px(bits: *mut u8, w: i32, h: i32, x: i32, y: i32, bgra: u32) {
        if x < 0 || y < 0 || x >= w || y >= h {
            return;
        }
        let i = (y * w + x) as usize;
        unsafe {
            *(bits.add(i * 4) as *mut u32) = bgra;
        }
    }

    // Icono simple: "impresora" azul con hoja blanca de papel.
    fn draw_icon(bits: *mut u8, w: i32, h: i32) {
        let blue = 0xFF0F6BEDu32; // BGRA
        let white = 0xFFFFFFFFu32;
        let paperline = 0xFF0F6BEDu32;
        let transparent = 0x00000000u32;
        let r = w.min(h) / 5; // radio de esquina

        for y in 0..h {
            for x in 0..w {
                let cx = if x < r {
                    r
                } else if x >= w - r {
                    w - r - 1
                } else {
                    -1
                };
                let cy = if y < r {
                    r
                } else if y >= h - r {
                    h - r - 1
                } else {
                    -1
                };
                if cx >= 0 && cy >= 0 {
                    let dx = x - cx;
                    let dy = y - cy;
                    if dx * dx + dy * dy > r * r {
                        set_px(bits, w, h, x, y, transparent);
                        continue;
                    }
                }
                set_px(bits, w, h, x, y, blue);
            }
        }

        // hoja de papel
        let ph = 2;
        let top = h / 2 - 3 * ph / 2;
        for y in top..(top + 3 * ph) {
            for x in 6..(w - 6) {
                set_px(bits, w, h, x, y, white);
            }
        }
        // líneas de texto en la hoja
        for i in 0..3 {
            let y = top + 1 + i * ph;
            for x in 8..(w - 8) {
                set_px(bits, w, h, x, y, paperline);
            }
        }
    }
}
