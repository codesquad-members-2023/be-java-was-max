package util;

import com.google.common.reflect.ClassPath;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public final class PackageExplorer {

    private PackageExplorer() {

    }

    // google guava 라이브러리를 이용한 패키지 내의 클래스 탐색
    public static Set<Class> findAllClassesUsingGoogleGuice(String packageName) throws IOException {
        return ClassPath.from(ClassLoader.getSystemClassLoader())
            .getAllClasses()
            .stream()
            .filter(clazz -> clazz.getPackageName()
                .equalsIgnoreCase(packageName))
            .map(clazz -> clazz.load())
            .collect(Collectors.toSet());
    }
}
