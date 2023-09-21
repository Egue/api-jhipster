package com.comunicamosmas.api.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.comunicamosmas.api.domain.HanRetiros;
import com.comunicamosmas.api.domain.SystemConfig;
import com.comunicamosmas.api.service.IHanRetirosService;
import com.comunicamosmas.api.service.ISystemConfigService;
import com.comunicamosmas.api.service.dto.AnoWithListIntegerDTO;
import com.comunicamosmas.api.service.dto.ChartDataLineDTO;
import com.comunicamosmas.api.service.dto.GrupoMailDTO;
import com.comunicamosmas.api.service.dto.HanRetirosCommentsDTO;
import com.comunicamosmas.api.service.dto.HanRetirosDTO;
import com.comunicamosmas.api.service.dto.HanRetirosDTOPOST;
import com.comunicamosmas.api.service.dto.GrupoMailDTO.Grupos;
import com.comunicamosmas.api.web.rest.errors.ExceptionNullSql;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/controlmas")
public class HanRetirosController {

    @Autowired
    IHanRetirosService retirosService;

    @Autowired
    ISystemConfigService configService;

    @PostMapping("/seguimiento/retiros")
    public ResponseEntity<?> save(@RequestBody HanRetirosDTOPOST retiros) {
        Map<String, Object> response = new HashMap<>();

        try {
            HanRetiros han_retiros = new HanRetiros();
            han_retiros.setTipo(retiros.getTipo());
            han_retiros.setDescripcion(retiros.getDescripcion());
            han_retiros.setIdUser(retiros.getIdUser());
            han_retiros.setEstado(retiros.getEstado());
            han_retiros.setIdContrato(retiros.getIdContrato());
            han_retiros.setIdPadre(retiros.getIdPadre());
            ;

            // HanRetiros result = retirosService.save(retiros);

            if (retiros.getSelectedGrupo() != null) {
                List<String> grupo = new ArrayList<>();
                List<String> email = new ArrayList<>();

                for (GrupoMailDTO.Grupos rs : retiros.getSelectedGrupo()) {
                    grupo.add(rs.getName());
                    for (GrupoMailDTO.Correo correo : rs.getCorreos()) {
                        email.add(correo.getEmail());
                    }
                }

                String separadoPorComa = String.join(",", grupo);

                han_retiros.setReportado(separadoPorComa);

                HanRetiros save = retirosService.save(han_retiros);

                retirosService.sendMailRetiros(save.getId(), email);
            } else {

                retirosService.save(han_retiros);
            }

            response.put("response", "creado");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/seguimiento/retiros/{idContrato}")
    public ResponseEntity<?> findAll(@PathVariable Long idContrato) {
        Map<String, Object> response = new HashMap<>();

        try {

            List<HanRetirosDTO> result = retirosService.findByIdContrato(idContrato);

            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (ExceptionNullSql e) {
            response.put("response", e.getMessage() + ":" + e.getDetails());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // buscar comentarios
    @GetMapping("/seguimiento/retiros/message/{idPadre}")
    public ResponseEntity<?> messages(@PathVariable Integer idPadre) {
        Map<String, Object> response = new HashMap<>();

        try {

            List<HanRetirosCommentsDTO> result = retirosService.messageRetiros(idPadre);

            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (ExceptionNullSql e) {
            response.put("response", e.getMessage() + ":" + e.getDetails());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    // cargar imagen
    @PostMapping(value = "/seguimiento/img", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> fileImg(@RequestParam("file") MultipartFile file, @RequestParam("idUser") Long idUser,
            @RequestParam("idPadre") Long idPadre) {
        Map<String, Object> response = new HashMap<>();

        try {

            HanRetiros retiros = new HanRetiros();
            retiros.setIdPadre(idPadre);
            retiros.setIdUser(idUser);
            retirosService.uploadImg(file, retiros);
            response.put("response", "ok");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (ExceptionNullSql e) {
            response.put("response", e.getMessage() + ":" + e.getDetails());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/seguimiento/render/img/{name}")
    public ResponseEntity<?> getMain(@PathVariable String name) {

        try {
            SystemConfig ruta = configService.findByOrigen("retiro_img");
            String path = ruta.getComando() + name;

            Path imagePath = Paths.get(path);

            Resource imageResource = new UrlResource(imagePath.toUri());

            if (imageResource.exists() && imageResource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) // o MediaType.IMAGE_PNG, según el tipo de imagen
                        .body(imageResource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Manejo de errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/seguimiento/retiros/close/{id}")
    public ResponseEntity<?> close(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {

            retirosService.changeCerrado(id);

            response.put("response", "Actualizado");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (ExceptionNullSql e) {
            response.put("response", e.getMessage() + ":" + e.getDetails());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/seguimiento/retiros/chartLine")
    public ResponseEntity<?> charLine(@RequestBody AnoWithListIntegerDTO datos) {
        Map<String, Object> response = new HashMap<>();

        try {

            ChartDataLineDTO result = retirosService.chartLine(datos.getAno(), datos.getArrayInteger());

            response.put("response", result);

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (ExceptionNullSql e) {
            response.put("response", e.getMessage() + ":" + e.getDetails());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("response", e.getMessage());

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/seguimiento/retiros/send_mails/{id}")
    public ResponseEntity<?> sendMails(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {

            // retirosService.sendMailRetiros(id);

            response.put("response", "Se ha enviado el correo de notificación ");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (ExceptionNullSql e) {
            response.put("response", e.getMessage() + ":" + e.getDetails());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            // TODO: handle exception
            response.put("response", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/seguimiento/retiros/addarea/{id}")
    public ResponseEntity<?> addArea(@RequestBody List<GrupoMailDTO.Grupos> areas, @PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {

            // retirosService.sendMailRetiros(id);
            HanRetiros retiros = retirosService.findById(id);
            // separar por coma reportados
            String[] separados = retiros.getReportado().split(",");
            // anexar separadas a List<String> grupo
            List<String> grupo = new ArrayList<>(Arrays.asList(separados));

            List<String> correo = new ArrayList<>();

            for (GrupoMailDTO.Grupos rs : areas) {
                grupo.add(rs.getName());
                //System.out.println(rs.getName());
                for (GrupoMailDTO.Correo res : rs.getCorreos()) {
                    correo.add(res.getEmail());
                    //System.out.println(res.getEmail());
                }
            }
            String separadoPorComa = String.join(",", grupo);
            retiros.setReportado(separadoPorComa);
            retirosService.save(retiros);
            retirosService.sendMailRetiros(id, correo);

            response.put("response", "Se ha enviado el correo de notificación ");

            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

        } catch (ExceptionNullSql e) {
            response.put("response", e.getMessage() + ":" + e.getDetails());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            // TODO: handle exception
            response.put("response", e.getMessage());
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
