package webserver.util;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

public final class PackageExplorer {

    private PackageExplorer() {
        throw new RuntimeException("PackageExplorer 클래스는 유틸 클래스입니다.");
    }

    public static Set<Class> scanClasses(String basePackage) throws IOException {
        return ClassPath.from(ClassLoader.getSystemClassLoader())
                .getTopLevelClassesRecursive(basePackage)
                .stream()
                .map(ClassInfo::load)
                .collect(Collectors.toSet());
    }
}
