package cs2110;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayUtilitiesCopy2DRangeTest {

    // -------- helpers --------
    private static int[][] deepCopy(int[][] a) {
        if (a == null) return null;
        int[][] b = new int[a.length][];
        for (int i = 0; i < a.length; i++) {
            b[i] = (a[i] == null) ? null : java.util.Arrays.copyOf(a[i], a[i].length);
        }
        return b;
    }

    /** expected, actual (keeps IDE happy about argument order) */
    private static void assert2DEquals(int[][] expected, int[][] actual, String msg) {
        if (expected == null || actual == null) {
            assertEquals(expected, actual, msg);
            return;
        }
        assertEquals(expected.length, actual.length, msg + " (outer length)");
        for (int i = 0; i < expected.length; i++) {
            if (expected[i] == null || actual[i] == null) {
                assertEquals(expected[i], actual[i], msg + " (row " + i + ")");
            } else {
                assertArrayEquals(expected[i], actual[i], msg + " (row " + i + ")");
            }
        }
    }

    // ======================= SUCCESS CASES =======================

    @DisplayName("WHEN a 1x1 region is copied, THEN only that cell in dst changes; src unchanged; returns true")
    @Test
    void copySingleCell() {
        int[][] src = {{10, 11, 12}, {20, 21, 22}};
        int[][] dst = {{0, 1, 2}, {3, 4, 5}};
        int[][] srcBefore = deepCopy(src);

        assertTrue(ArrayUtilities.copy2DRange(src, 0, 1, dst, 1, 2, 1, 1));

        assert2DEquals(srcBefore, src, "Source array should not be modified");
        int[][] expected = {{0, 1, 2}, {3, 4, 11}};
        assert2DEquals(expected, dst, "Destination array incorrect after 1x1 copy");
    }

    @DisplayName("WHEN copying a full-width 1-row block, THEN correct slice is written and returns true")
    @Test
    void copyFullWidthSingleRow() {
        int[][] src = {{9, 8, 7, 6}, {5, 4, 3, 2}};
        int[][] dst = {{0, 0, 0, 0}, {1, 1, 1, 1}};
        int[][] srcBefore = deepCopy(src);

        assertTrue(ArrayUtilities.copy2DRange(src, 1, 0, dst, 0, 0, 1, 4));

        assert2DEquals(srcBefore, src, "Source should not change");
        int[][] expected = {{5, 4, 3, 2}, {1, 1, 1, 1}};
        assert2DEquals(expected, dst, "Destination incorrect for full-row copy");
    }

    @DisplayName("WHEN copying a full-height 1-column block, THEN correct column is written and returns true")
    @Test
    void copyFullHeightSingleCol() {
        int[][] src = {{1, 2, 3}, {10, 20, 30}, {100, 200, 300}};
        int[][] dst = {{7, 7, 7}, {8, 8, 8}, {9, 9, 9}};
        int[][] srcBefore = deepCopy(src);

        assertTrue(ArrayUtilities.copy2DRange(src, 0, 1, dst, 0, 0, 3, 1));

        assert2DEquals(srcBefore, src, "Source should not change");
        int[][] expected = {{2, 7, 7}, {20, 8, 8}, {200, 9, 9}};
        assert2DEquals(expected, dst, "Destination incorrect for full-column copy");
    }

    @DisplayName("WHEN copying an interior HxW block, THEN exactly that rectangle changes in dst and returns true")
    @Test
    void copyInteriorRectangle() {
        int[][] src = {
                {0, 1, 2, 3, 4},
                {10, 11, 12, 13, 14},
                {20, 21, 22, 23, 24},
                {30, 31, 32, 33, 34}
        };
        int[][] dst = {
                {100, 101, 102, 103, 104},
                {110, 111, 112, 113, 114},
                {120, 121, 122, 123, 124},
                {130, 131, 132, 133, 134}
        };
        int[][] srcBefore = deepCopy(src);

        assertTrue(ArrayUtilities.copy2DRange(src, 1, 2, dst, 0, 1, 2, 3));

        assert2DEquals(srcBefore, src, "Source should not change");
        int[][] expected = {
                {100, 12, 13, 14, 104},
                {110, 22, 23, 24, 114},
                {120, 121, 122, 123, 124},
                {130, 131, 132, 133, 134}
        };
        assert2DEquals(expected, dst, "Destination interior copy incorrect");
    }

    @DisplayName("WHEN copying region touching bottom-right edges, THEN boundaries are handled and returns true")
    @Test
    void copyAtEdges() {
        int[][] src = {{1, 2, 3}, {4, 5, 6}};
        int[][] dst = {{9, 9, 9}, {9, 9, 9}};
        int[][] srcBefore = deepCopy(src);

        assertTrue(ArrayUtilities.copy2DRange(src, 1, 1, dst, 0, 0, 1, 2));

        assert2DEquals(srcBefore, src, "Source should not change");
        int[][] expected = {{5, 6, 9}, {9, 9, 9}};
        assert2DEquals(expected, dst, "Edge copy incorrect");
    }

    @DisplayName("WHEN height=0 or width=0, THEN it is a valid no-op that returns true and changes nothing")
    @Test
    void zeroSizedRegionsAreNoOps() {
        int[][] src = {{1, 2}, {3, 4}};
        int[][] dst = {{9, 9}, {9, 9}};
        int[][] sb = deepCopy(src), db = deepCopy(dst);

        assertTrue(ArrayUtilities.copy2DRange(src, 0, 0, dst, 1, 1, 0, 2));
        assertTrue(ArrayUtilities.copy2DRange(src, 0, 1, dst, 0, 0, 2, 0));

        assert2DEquals(sb, src, "Source should not change");
        assert2DEquals(db, dst, "Destination should not change");
    }

    @DisplayName("WHEN copying within the same array (non-overlapping), THEN copy is correct and returns true")
    @Test
    void sameArrayNonOverlapping() {
        int[][] a = {{0, 1, 2, 3}, {4, 5, 6, 7}};
        int[][] before = deepCopy(a);

        assertTrue(ArrayUtilities.copy2DRange(a, 0, 1, a, 1, 1, 1, 2));

        int[][] expected = {{0, 1, 2, 3}, {4, 1, 2, 7}};
        assert2DEquals(expected, a, "Same-array non-overlapping copy incorrect");
        assertArrayEquals(before[0], a[0], "Source row should remain unchanged");
    }

    @DisplayName("WHEN overlapping copy down/right in same array, THEN behavior is as-if via temp-buffer and returns true")
    @Test
    void sameArrayOverlappingDownRight() {
        int[][] a = {
                {10, 11, 12, 13},
                {20, 21, 22, 23},
                {30, 31, 32, 33}
        };

        assertTrue(ArrayUtilities.copy2DRange(a, 0, 1, a, 1, 2, 2, 2));

        int[][] expected = {
                {10, 11, 12, 13},
                {20, 21, 11, 12},
                {30, 31, 21, 22}
        };
        assert2DEquals(expected, a, "Overlapping (down/right) copy incorrect");
        assertEquals(11, a[1][2]);
        assertEquals(12, a[1][3]);
        assertEquals(21, a[2][2]);
        assertEquals(22, a[2][3]);
    }

    @DisplayName("WHEN overlapping copy up/left in same array, THEN behavior is as-if via temp-buffer and returns true")
    @Test
    void sameArrayOverlappingUpLeft() {
        int[][] a = {
                {10, 11, 12, 13},
                {20, 21, 22, 23},
                {30, 31, 32, 33}
        };

        assertTrue(ArrayUtilities.copy2DRange(a, 1, 2, a, 0, 1, 2, 2));

        int[][] expected = {
                {10, 22, 23, 13},
                {20, 32, 33, 23},
                {30, 31, 32, 33}
        };
        assert2DEquals(expected, a, "Overlapping (up/left) copy incorrect");
        // spot check key cells to ensure copy direction did not smear values
        assertEquals(22, a[0][1]);
        assertEquals(23, a[0][2]);
        assertEquals(32, a[1][1]);
        assertEquals(33, a[1][2]);
    }

    @DisplayName("WHEN copying between jagged arrays (valid region), THEN it copies correctly and returns true")
    @Test
    void jaggedArraysValid() {
        // Example aligned with the assignment’s jagged illustration
        int[][] grid1 = {
                {1, 3, 6, 4, 2},   // length 5
                {9, 5, 4, 6},      // length 4
                {2, 8, 7}          // length 3
        };
        int[][] grid2 = {
                {1, 8, 7, 2},
                {4, 3, 6, 9},
                {5, 1, 2, 9},
                {3, 7, 2, 8}
        };
        int[][] g1Before = deepCopy(grid1);

        // Copy height=2, width=3: grid1[0..1][1..3] -> grid2[2..3][0..2]
        assertTrue(ArrayUtilities.copy2DRange(grid1, 0, 1, grid2, 2, 0, 2, 3));

        assert2DEquals(g1Before, grid1, "Source (jagged) should not change");
        int[][] expectedGrid2 = {
                {1, 8, 7, 2},
                {4, 3, 6, 9},
                {3, 6, 4, 9},
                {5, 4, 6, 8}
        };
        assert2DEquals(expectedGrid2, grid2, "Jagged valid copy incorrect");
    }

    @DisplayName("WHEN src and dst are the exact same region, THEN it is a no-op and returns true")
    @Test
    void sameArrayExactSameRegion() {
        int[][] a = {{1, 2, 3}, {4, 5, 6}};
        int[][] before = deepCopy(a);

        assertTrue(ArrayUtilities.copy2DRange(a, 0, 0, a, 0, 0, 2, 3));
        assert2DEquals(before, a, "Same-region copy should not change the array");
    }

    // ======================= FAILURE / VALIDATION =======================

    @DisplayName("WHEN src is null or dst is null, THEN returns false and the other array remains unchanged")
    @Test
    void nullSrcOrDst() {
        int[][] dst = {{0}};
        int[][] dstBefore = deepCopy(dst);
        assertFalse(ArrayUtilities.copy2DRange(null, 0, 0, dst, 0, 0, 1, 1));
        assert2DEquals(dstBefore, dst, "Destination must remain unchanged");

        int[][] src = {{1}};
        int[][] srcBefore = deepCopy(src);
        assertFalse(ArrayUtilities.copy2DRange(src, 0, 0, null, 0, 0, 1, 1));
        assert2DEquals(srcBefore, src, "Source must remain unchanged");
    }

    @DisplayName("WHEN any index is negative, THEN returns false and neither array changes")
    @Test
    void negativeIndices() {
        int[][] src = {{1, 2}, {3, 4}};
        int[][] dst = {{9, 9}, {9, 9}};
        int[][] sb = deepCopy(src), db = deepCopy(dst);

        assertFalse(ArrayUtilities.copy2DRange(src, -1, 0, dst, 0, 0, 1, 1));
        assertFalse(ArrayUtilities.copy2DRange(src, 0, -1, dst, 0, 0, 1, 1));
        assertFalse(ArrayUtilities.copy2DRange(src, 0, 0, dst, -1, 0, 1, 1));
        assertFalse(ArrayUtilities.copy2DRange(src, 0, 0, dst, 0, -1, 1, 1));

        assert2DEquals(sb, src, "Source must remain unchanged");
        assert2DEquals(db, dst, "Destination must remain unchanged");
    }

    @DisplayName("WHEN height < 0 or width < 0, THEN returns false and nothing changes")
    @Test
    void negativeHeightOrWidth() {
        int[][] src = {{1, 2}, {3, 4}};
        int[][] dst = {{9, 9}, {9, 9}};
        int[][] sb = deepCopy(src), db = deepCopy(dst);

        assertFalse(ArrayUtilities.copy2DRange(src, 0, 0, dst, 0, 0, -1, 1));
        assertFalse(ArrayUtilities.copy2DRange(src, 0, 0, dst, 0, 0, 1, -1));

        assert2DEquals(sb, src, "Source unchanged");
        assert2DEquals(db, dst, "Destination unchanged");
    }

    @DisplayName("WHEN srcI + height exceeds src.length OR dstI + height exceeds dst.length, THEN returns false")
    @Test
    void rowOverflow() {
        int[][] src = {{1, 2}, {3, 4}};
        int[][] dst = {{9, 9}};
        int[][] sb = deepCopy(src), db = deepCopy(dst);

        assertFalse(ArrayUtilities.copy2DRange(src, 1, 0, dst, 0, 0, 2, 1),
                "src bottom overflow should be invalid");
        assertFalse(ArrayUtilities.copy2DRange(src, 0, 0, dst, 0, 0, 2, 1),
                "dst bottom overflow should be invalid");

        assert2DEquals(sb, src, "Source unchanged");
        assert2DEquals(db, dst, "Destination unchanged");
    }

    @DisplayName("WHEN any src row in region is too short for width, THEN returns false (ragged invalid)")
    @Test
    void srcColumnOverflowOnRagged() {
        int[][] src = {{1}, {3, 4}};  // row 0 too short for width=2 at j=0
        int[][] dst = {{9, 9}, {9, 9}};
        int[][] sb = deepCopy(src), db = deepCopy(dst);

        assertFalse(ArrayUtilities.copy2DRange(src, 0, 0, dst, 0, 0, 1, 2));

        assert2DEquals(sb, src, "Source unchanged");
        assert2DEquals(db, dst, "Destination unchanged");
    }

    @DisplayName("WHEN any dst row in region is too short for width, THEN returns false (ragged invalid)")
    @Test
    void dstColumnOverflowOnRagged() {
        int[][] src = {{1, 2}, {3, 4}};
        int[][] dst = {{9}, {9, 9}};  // row 0 too short for width=2
        int[][] sb = deepCopy(src), db = deepCopy(dst);

        assertFalse(ArrayUtilities.copy2DRange(src, 0, 0, dst, 0, 0, 1, 2));

        assert2DEquals(sb, src, "Source unchanged");
        assert2DEquals(db, dst, "Destination unchanged");
    }

    @DisplayName("WHEN starting srcJ or dstJ is beyond row length for any affected row, THEN returns false")
    @Test
    void startColumnOutOfBoundsOnShortRows() {
        int[][] src = {{1}, {10, 11}};
        int[][] dst = {{0, 0}, {0, 0}};
        int[][] sb = deepCopy(src), db = deepCopy(dst);

        assertFalse(ArrayUtilities.copy2DRange(src, 0, 1, dst, 0, 0, 1, 1),
                "srcJ out of bounds on first row should be invalid");
        assertFalse(ArrayUtilities.copy2DRange(src, 1, 0, dst, 0, 2, 1, 1),
                "dstJ out of bounds on first dst row should be invalid");

        assert2DEquals(sb, src, "Source unchanged");
        assert2DEquals(db, dst, "Destination unchanged");
    }

    @DisplayName("WHEN height/width are so large that region would overflow columns/rows, THEN returns false")
    @Test
    void largeRegionOverflows() {
        int[][] src = {{1, 2, 3}, {4, 5, 6}};
        int[][] dst = {{0, 0, 0}, {0, 0, 0}};
        int[][] sb = deepCopy(src), db = deepCopy(dst);

        assertFalse(ArrayUtilities.copy2DRange(src, 0, 0, dst, 0, 0, 3, 2));
        assertFalse(ArrayUtilities.copy2DRange(src, 0, 0, dst, 0, 0, 2, 4));
        assertFalse(ArrayUtilities.copy2DRange(src, 1, 2, dst, 0, 0, 1, 2));

        assert2DEquals(sb, src, "Source unchanged");
        assert2DEquals(db, dst, "Destination unchanged");
    }

    @DisplayName("WHEN any src or dst row in region is null, THEN returns false and nothing changes")
    @Test
    void nullRowInEitherRegion() {
        int[][] src = {{1, 2, 3}, null, {7, 8, 9}};
        int[][] dst = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
        int[][] db = deepCopy(dst);

        assertFalse(ArrayUtilities.copy2DRange(src, 0, 0, dst, 0, 0, 2, 2),
                "Null src row should invalidate");
        assert2DEquals(db, dst, "Destination must remain unchanged");

        int[][] src2 = {{1, 2, 3}, {4, 5, 6}};
        int[][] dst2 = {{0, 0, 0}, null, {0, 0, 0}};
        int[][] sb2 = deepCopy(src2), db2 = deepCopy(dst2);

        assertFalse(ArrayUtilities.copy2DRange(src2, 0, 0, dst2, 0, 0, 2, 2),
                "Null dst row should invalidate");
        assert2DEquals(sb2, src2, "Source must remain unchanged");
        assert2DEquals(db2, dst2, "Destination must remain unchanged");
    }

    @DisplayName("WHEN height=0 AND width=0, THEN it is a no-op returning true (both arrays unchanged)")
    @Test
    void zeroByZeroNoOp() {
        int[][] src = {{1, 2}, {3, 4}};
        int[][] dst = {{5, 6}, {7, 8}};
        int[][] sb = deepCopy(src), db = deepCopy(dst);

        assertTrue(ArrayUtilities.copy2DRange(src, 0, 0, dst, 1, 1, 0, 0));

        assert2DEquals(sb, src, "Source unchanged");
        assert2DEquals(db, dst, "Destination unchanged");
    }
}
