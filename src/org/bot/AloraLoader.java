package org.bot;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

import org.bot.boot.Constants;
import org.bot.loader.ServerLoader;
import org.bot.util.reflection.Modifiers;
import org.bot.util.reflection.ReflectedClass;
import org.bot.util.reflection.ReflectedField;

public class AloraLoader extends ServerLoader<JFrame> {

	public AloraLoader() throws IOException {
		super(Constants.JAR_URL, "Alora");
	}

	@Override
	public JFrame loadProtocol() throws IllegalArgumentException, IllegalAccessException {
		ReflectedClass clientLoader;
		ReflectedField frameField;
		try {
			clientLoader = this.getClass("ClientLoader")
					.getConstructor(new Modifiers.ModifierBuilder().parameterTypes(String.class, boolean.class).build())
					.getNewInstance("1", true);
			frameField = clientLoader.getField(new Modifiers.ModifierBuilder().name("add").build());
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
		return (JFrame) frameField.getValue();
	}

}
