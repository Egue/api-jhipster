<?php
function limpiarTexto(string $valor): string {
    $converted = iconv('Windows-1252', 'UTF-8//IGNORE', $valor);
    return $converted !== false ? trim($converted) : trim($valor);
}

function convertirFecha(string $fecha): string {
    // Convierte 09/04/2026 → 2026-04-09
    $partes = explode('/', $fecha);
    return "{$partes[2]}-{$partes[1]}-{$partes[0]}";
}

function tipoIdentificacion(string $tipoPersona): int {
    // 13 = Cédula (Natural), 32 = NIT (Jurídica)
    return str_contains(strtoupper($tipoPersona), 'NATURAL') ? 13 : 31;
}

function tipoPersonaDian(string $tipoPersona): int {
    // 2 = Natural, 1 = Jurídica
    return str_contains(strtoupper($tipoPersona), 'NATURAL') ? 2 : 1;
}

function csvToJson(string $filePath, string $delimiter = ','): string {
    $facturas = [];

    if (!file_exists($filePath)) {
        throw new Exception("Archivo no encontrado: $filePath");
    }

    if (($handle = fopen($filePath, 'r')) !== false) {

        // Eliminar BOM si existe
        $bom = fread($handle, 3);
        if ($bom !== "\xEF\xBB\xBF") {
            rewind($handle);
        }

        $headers = fgetcsv($handle, 0, $delimiter, '"', '\\');
        $headers = array_map('limpiarTexto', $headers);

        while (($data = fgetcsv($handle, 0, $delimiter, '"', '\\')) !== false) {
            if (count($headers) !== count($data)) continue;

            $row = array_combine($headers, $data);
            $row = array_map('limpiarTexto', $row);

            $numFact = (int) $row['NUM FACT'];

            // Limpiar TOTAL: "$ 55,000" → 50000.00
            $total = preg_replace('/[^\d]/', '', $row['TOTAL']);
            $totalFormateado = number_format((float)$total, 2, '.', '');

            // Agrupar por NUM FACT
            if (!isset($facturas[$numFact])) {
                $facturas[$numFact] = [
                    "id_empresa"                    => "165",
                    "tipo_pago"                     => "0",
                    "token_empresa"                 => "eab89322e3291936e22e11199fcae7aae8acd9ba",
                    "operacion"                     => "factura",
                    "tipo_operacion"                => 1,
                    "nota_factura"                  => "servicios de Telecomunicaciones",
                    "factura_numero"                => $numFact,
                    "fecha_emite"                   => "2026-07-15",
                    "hora_emite"                    => "07:58:49-05:00",
                    "cliente_nombre"                => $row['NOMBRES / RAZON SOCIAL'],
                    "cliente_municipio_nombre"      => $row['MUNICIPIO'],
                    "cliente_municipio_codigo"      => "85001",
                    "cliente_departamento_nombre"   => "CASANARE",
                    "cliente_departamento_codigo"   => "85",
                    "cliente_direccion"             => $row['DIRECCIÓN'],
                    "cliente_tipo_persona"          => tipoPersonaDian($row['TIPO DE PERSONA']),
                    "cliente_tipo_identificacion"   => tipoIdentificacion($row['TIPO DE PERSONA']),
                    "cliente_identificacion"        => $row['IDENTIDAD'],
                    "cliente_correo"                => $row['email'],
                    "productos"                     => [],
                    "iva_porcentaje"                => 19,
                    "ipc_porcentaje"                => 8,
                    "config_genera_pdf"             => 0,
                    "config_envia_correo"           => 0
                ];
            }

            // Agregar producto (puede haber varios por factura)
            $numProducto = count($facturas[$numFact]['productos']) + 1;
            $iva = "0";
            if($row['GRAVADO'] == "Si") { $iva = "19"; }
            $facturas[$numFact]['productos'][(string)$numProducto] = [
                "producto_nombre"           => "Mensualidad " . $row['CONTRATO'],
                "producto_valor_unidad"     => $totalFormateado,
                "producto_cantidad"         => 1,
                "producto_iva_porcentaje"   =>  $iva,
                "producto_imp_porcentaje"   => 0,
                "producto_codigo_producto"  => "83121703"
            ];
        }

        fclose($handle);
    }

    // Reindexar como array limpio
    $resultado = array_values($facturas);

    return json_encode($resultado, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE);
}

// Uso
$json = csvToJson('facturas_email_julio_2.csv');
file_put_contents('datos.json', $json);
echo "JSON generado: " . count(json_decode($json)) . " facturas\n";