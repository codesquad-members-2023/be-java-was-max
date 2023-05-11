package com.baeldung.reflection.access.packages.search;

import com.google.common.reflect.ClassPath;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AccessingAllClassesInPackage {

    public Set<Class> findAllClassesUsingClassLoader(String packageName) {
        InputStream stream = ClassLoader.getSystemClassLoader()
            .getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        return reader.lines()
            .filter(line -> line.endsWith(".class"))
            .map(line -> getClass(line, packageName))
            .collect(Collectors.toSet());
    }

    private Class getClass(String className, String packageName) {
        try {
            return Class.forName(packageName + "."
                + className.substring(0, className.lastIndexOf('.')));
        } catch (ClassNotFoundException e) {
            // handle the exception
        }
        return null;
    }

    // google guava 라이브러리를 이용한 패키지 내의 클래스 탐색
    public Set<Class> findAllClassesUsingGoogleGuice(String packageName) throws IOException {
        return ClassPath.from(ClassLoader.getSystemClassLoader())
            .getAllClasses()
            .stream()
            .filter(clazz -> clazz.getPackageName()
                .equalsIgnoreCase(packageName))
            .map(clazz -> clazz.load())
            .collect(Collectors.toSet());
    }

    @Test
    @DisplayName("classloader를 사용하여 모든 클래스를 탐색할때 성공한다")
    public void when_findAllClassesUsingClassLoader_thenSuccess() {
        // given
        AccessingAllClassesInPackage instance = new AccessingAllClassesInPackage();
        // when
        Set<Class> classes = instance.findAllClassesUsingClassLoader(
            "com.baeldung.reflection.access.packages.search");
        // then
        Assertions.assertEquals(4, classes.size());
    }

    @Test
    @DisplayName("google guava 라이브러리를 이용하여 클래스 탐색할때 성공한다.")
    public void when_findAllClassesUsingGoogleGuice_thenSuccess() throws IOException {
        AccessingAllClassesInPackage instance = new AccessingAllClassesInPackage();

        Set<Class> classes = instance.findAllClassesUsingGoogleGuice(
            "com.baeldung.reflection.access.packages.search");

        Assertions.assertEquals(4, classes.size());
    }

}
