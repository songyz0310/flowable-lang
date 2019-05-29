package com.songyz.flowable.common;

import java.util.Locale;

public class Constants {
    public static enum Gender {
        WOMAN, MAN;
    }
    
    public static enum Language {
        ZH(Locale.CHINESE), EN(Locale.ENGLISH);
        
        private Locale locale;
        Language(Locale locale) {
            this.locale = locale;
        }
        
        public Locale getLocale() {
            return this.locale;
        }
    }
}
