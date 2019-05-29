package com.songyz.toolkits;

import com.songyz.flowable.validator.i1stcs.ErrorDesc;

public class App {

    public static void main(String[] args) {
        String i18n = ErrorDesc.ALL_PROCESS_DEFINITIONS_NOT_EXECUTABLE.getI18n();
        System.out.println(i18n);

    }

}
