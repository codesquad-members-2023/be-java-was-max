package util;

import java.io.IOException;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class PackageExplorerTest {

    @Test
    @DisplayName("특정 패키지를 기준으로 하위 패키지안에 있는 클래스들을 스캔합니다.")
    public void test1() throws IOException {
        // given

        // when
        Set<Class> cafe = PackageExplorer.scanClasses("cafe");
        // then
        Assertions.assertThat(cafe.size()).isGreaterThan(0);
    }
}
