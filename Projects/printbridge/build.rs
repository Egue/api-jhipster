fn main() {
    let ico = std::path::Path::new("assets/icon.ico");
    if ico.exists() {
        embed_resource::compile("assets/icon.rc", embed_resource::NONE);
    } else {
        println!("cargo:warning=assets/icon.ico no existe; el .exe quedará sin icono. Coloca tu icono (.ico) con ese nombre y recompila.");
    }
}