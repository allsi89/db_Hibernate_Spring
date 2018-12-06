package io.consoleIO;

import io.Writer;

public class ConsoleWriter implements Writer {


    @Override
    public void print(String output) {
        System.out.print(output);
    }

    @Override
    public void println(String output) {
        System.out.println(output);
    }
}
