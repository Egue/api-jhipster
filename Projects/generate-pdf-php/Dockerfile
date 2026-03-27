# Usar la imagen oficial de PHP 8.2 con Apache
FROM php:8.2-apache

# Instalar las dependencias necesarias
RUN apt-get update && apt-get install -y \
    libfreetype6-dev \
    libjpeg62-turbo-dev \
    libpng-dev \
    libwebp-dev \
    libxpm-dev \
    libonig-dev \
    libzip-dev \
    unzip \
    && docker-php-ext-configure gd \
        --with-freetype \
        --with-jpeg \
        --with-webp \
    && docker-php-ext-install -j$(nproc) gd pdo pdo_mysql zip mbstring

# Instalar Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# Configurar permisos y carpeta de trabajo
WORKDIR /var/www/html

# Opcional: Copiar archivos del proyecto (ajusta según tu estructura)
# COPY . /var/www/html

# Exponer el puerto 80 (por defecto en Apache)
EXPOSE 80

# Habilitar el módulo de reescritura de Apache (opcional)
RUN a2enmod rewrite
