package com.comunicamosmas.api.service.dto;

import java.util.List;

public class MenuDTOJson {

    public class Menu{

        private String title;

        private String icon;

        private boolean isActive;

        private List<SubMenu> subMenu;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public boolean isActive() {
            return isActive;
        }

        public void setActive(boolean isActive) {
            this.isActive = isActive;
        }

        public List<SubMenu> getSubMenu() {
            return subMenu;
        }

        public void setSubMenu(List<SubMenu> subMenu) {
            this.subMenu = subMenu;
        }

        
    }

    public class SubMenu {
        
        private String tittle;

        private String icon;

        private String url;

        public String getTittle() {
            return tittle;
        }

        public void setTittle(String tittle) {
            this.tittle = tittle;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        
    }
    
}
