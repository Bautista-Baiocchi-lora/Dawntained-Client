package org.bot.component.inputs;

import org.bot.Engine;
import org.bot.util.Condition;
import org.bot.util.Random;

import java.awt.event.KeyEvent;


public class Keyboard {

    public static void pressEnter() {
        press(KeyEvent.VK_ENTER);
        release(KeyEvent.VK_ENTER);
    }


    public static void press(int event) {
        Engine.getKeyboard().press(Engine.getKeyboard().create(KeyEvent.KEY_PRESSED, event, (char) event));
    }

    public static void release(int event) {
        Engine.getKeyboard().release(Engine.getKeyboard().create(KeyEvent.KEY_RELEASED, event, (char) event));
    }

    public static void type(char c) {
        Engine.getKeyboard().type(c);
    }

    public static void sendText(String text, boolean pressEnter, int minSleep, int maxSleep) {
        for (int i = 0; i < text.toCharArray().length; i++) {
            type(text.toCharArray()[i]);
            Condition.sleep(Random.nextInt(minSleep, maxSleep));
        }
        if (pressEnter)
            pressEnter();
    }

    public static void sendText(String text, boolean pressEnter) {
        for (int i = 0; i < text.toCharArray().length; i++) {
            type(text.toCharArray()[i]);
            Condition.sleep(100);
        }
        if (pressEnter)
            pressEnter();
    }
}