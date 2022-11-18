package ru.job4j;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class BoxTest {
    @Test
    void isThisSphere() {
        Box box = new Box(0, 10);
        String name = box.whatsThis();
        assertThat(name).isNotEmpty()
                .contains("Sp", "here")
                .startsWith("S")
                .endsWith("re")
                .isEqualTo("Sphere");
    }

    @Test
    void isThisTetrahedron() {
        Box box = new Box(4, 10);
        String name = box.whatsThis();
        assertThat(name).isNotEmpty()
                .contains("tr", "hed")
                .startsWith("Tet")
                .endsWith("ron")
                .isEqualTo("Tetrahedron");
    }

    @Test
    void isThisCube() {
        Box box = new Box(8, 10);
        String name = box.whatsThis();
        assertThat(name).isNotEmpty()
                .contains("ub")
                .startsWith("Cu")
                .endsWith("e")
                .isEqualTo("Cube");
    }

    @Test
    void isThisUNKNOWNwhenEdge0() {
        Box box = new Box(8, 0);
        String name = box.whatsThis();
        assertThat(name).isNotEmpty()
                .contains("obj")
                .startsWith("Un")
                .endsWith("ject")
                .isEqualTo("Unknown object");
    }

    @Test
    void isThisUNKNOWNwhenSuchVertexNotExist() {
        Box box = new Box(7, 10);
        String name = box.whatsThis();
        assertThat(name).isNotEmpty()
                .contains("obj")
                .startsWith("Un")
                .endsWith("ject")
                .isEqualTo("Unknown object");
    }

    @Test
    void checkVertexOfSphere() {
        Box box = new Box(0, 10);
        int vertexOfSphere = box.getNumberOfVertices();
        assertThat(vertexOfSphere).isEqualTo(0);
    }

    @Test
    void checkVertexOfTetrahedron() {
        Box box = new Box(4, 10);
        int vertexOfTetrahedron = box.getNumberOfVertices();
        assertThat(vertexOfTetrahedron)
                .isNotZero()
                .isPositive()
                .isEven()
                .isGreaterThan(3)
                .isLessThan(5)
                .isEqualTo(4);
    }

    @Test
    void checkVertexOfCube() {
        Box box = new Box(8, 10);
        int vertexOfCube = box.getNumberOfVertices();
        assertThat(vertexOfCube)
                .isNotZero()
                .isPositive()
                .isEven()
                .isGreaterThan(7)
                .isLessThan(9)
                .isEqualTo(8);
    }

    @Test
    void isFigureExist() {
        Box box = new Box(8, 10);
        boolean result = box.isExist();
        assertThat(result).isTrue();
    }

    @Test
    void isFigureNotExist() {
        Box box = new Box(7, 10);
        boolean result = box.isExist();
        assertThat(result).isFalse();
    }

    @Test
    void checkCalculateSphereArea() {
        Box box = new Box(0, 10);
        double result = box.getArea();
        assertThat(result)
                .isEqualTo(1256.637d, withPrecision(0.00007d))
                .isCloseTo(1256.6d, withPrecision(0.04d))
                .isCloseTo(1256.637d, Percentage.withPercentage(0.01d))
                .isGreaterThan(1256.63d)
                .isLessThan(1256.638d);
    }

    @Test
    void checkCalculateTetrahedronArea() {
        Box box = new Box(4, 10);
        double result = box.getArea();
        assertThat(result)
                .isEqualTo(173.2d, withPrecision(0.0051d))
                .isCloseTo(173.205d, withPrecision(0.00009d))
                .isCloseTo(173.2d, Percentage.withPercentage(1.0d))
                .isGreaterThan(173.204d)
                .isLessThan(173.206d);
    }

    @Test
    void checkCalculateCubeArea() {
        Box box = new Box(8, 10);
        double result = box.getArea();
        assertThat(result)
                .isEqualTo(600.0d, withPrecision(0.0000000001))
                .isCloseTo(600.0d, withPrecision(0.0000000000001))
                .isCloseTo(600.0d, Percentage.withPercentage(1.0d))
                .isGreaterThan(599.999d)
                .isLessThan(600.001d);
    }

    @Test
    void checkCalculateNotExistFigureArea() {
        Box box = new Box(7, 10);
        double result = box.getArea();
        assertThat(result)
                .isEqualTo(0)
                .isLessThan(1)
                .isGreaterThan(-1);
    }
}