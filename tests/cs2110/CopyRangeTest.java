package cs2110;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 *
 *
 */

public class CopyRangeTest {

    @DisplayName("WHEN a range is copied from one array to another, THEN the correct elements are changed in the second array AND all other elements should remain the same AND the original array should remain unchanged AND returns true")
    @Test
    void testCorrectCopy() {
        int[] arr1 = {0, 1, 2, 3, 4, 5};
        int[] arr2 = {6, 7, 8, 9, 10, 11};
        assertTrue(ArrayUtilities.copyRange(arr1, 0, arr2, 1, 3),
                "Does not return true when range successfully copied");
        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5}, arr1, "Source array should not be modified");
        assertArrayEquals(new int[]{6, 0, 1, 2, 10, 11}, arr2, "Destination array incorrect");
    }

    @DisplayName("WHEN the entire source array is copied to a destination array of greater size, THEN the destination array contains the source range AND returns true")
    @Test
    void testEntireSrcArray() {
        int[] arr1 = {0, 1, 2, 3, 4, 5};
        int[] arr2 = {6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        assertTrue(ArrayUtilities.copyRange(arr1, 0, arr2, 0, 6),
                "Does not return true when range successfully copied");
        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5}, arr1, "Source array should not be modified");
        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5, 12, 13, 14, 15}, arr2,
                "Destination array incorrect");

        int[] arr3 = {0, 1, 2, 3, 4, 5};
        int[] arr4 = {6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        assertTrue(ArrayUtilities.copyRange(arr3, 0, arr4, 4, 6),
                "Does not return true when range successfully copied");
        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5}, arr3, "Source array should not be modified");
        assertArrayEquals(new int[]{6, 7, 8, 9, 0, 1, 2, 3, 4, 5}, arr4, "Destination array incorrect");
    }

    @DisplayName("WHEN a range of length n is from the source array is copied to a destination array of length n, THEN the destination array is correctly modified AND returns true")
    @Test
    void testEntireDstArray() {
        int[] arr1 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        int[] arr2 = {10, 11, 12, 13, 14};
        assertTrue(ArrayUtilities.copyRange(arr1, 0, arr2, 0, 5),
                "Does not return true when range successfully copied");
        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8}, arr1,
                "Source array should not be modified");
        assertArrayEquals(new int[]{0, 1, 2, 3, 4}, arr2, "Destination array incorrect");
    }

    @DisplayName("WHEN a range of length 0 is copied, then neither array is changed AND returns true")
    @Test
    void testZeroRange() {
        int[] arr1 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        int[] arr2 = {10, 11, 12, 13, 14};
        assertTrue(ArrayUtilities.copyRange(arr1, 0, arr2, 0, 0),
                "Does not return true when range successfully copied");
        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8}, arr1,
                "Source array should not be modified");
        assertArrayEquals(new int[]{10, 11, 12, 13, 14}, arr2,
                "Destination array should not be modified");

        //If start pos is > array length but length = 0, still returns true because no copy is performed

        int[] arr3 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        int[] arr4 = {10, 11, 12, 13, 14};
        assertTrue(ArrayUtilities.copyRange(arr3, -1, arr4, -1, 0),
                "Does not return true when range successfully copied");
        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8}, arr3,
                "Source array should not be modified");
        assertArrayEquals(new int[]{10, 11, 12, 13, 14}, arr4,
                "Destination array should not be modified");
    }

    @DisplayName("WHEN the source and destination arrays refer to the same array and valid inputs are provided, THEN the array is modified appropriately AND return true")
    @Test
    void testValidSameArray() {
        //Range length is greater than the difference between source and destination start indices - check for overwriting
        int[] arr1 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        int[] arr2 = arr1;
        assertTrue(ArrayUtilities.copyRange(arr1, 0, arr2, 2, 5),
                "Does not return true when range successfully copied");
        assertArrayEquals(new int[]{0, 1, 0, 1, 2, 3, 4, 7, 8}, arr1, "Range copied incorrectly");
        assertArrayEquals(new int[]{0, 1, 0, 1, 2, 3, 4, 7, 8}, arr2, "Range copied incorrectly");

        int[] arr3 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        int[] arr4 = arr3;
        assertTrue(ArrayUtilities.copyRange(arr3, 2, arr4, 2, 5),
                "Does not return true when range successfully copied");
        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8}, arr3, "Range copied incorrectly");
        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8}, arr3, "Range copied incorrectly");

        int[] arr5 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        int[] arr6 = arr5;
        assertTrue(ArrayUtilities.copyRange(arr5, 2, arr6, 0, 5),
                "Does not return true when range successfully copied");
        assertArrayEquals(new int[]{2, 3, 4, 5, 6, 5, 6, 7, 8}, arr5, "Range copied incorrectly");
        assertArrayEquals(new int[]{2, 3, 4, 5, 6, 5, 6, 7, 8}, arr6, "Range copied incorrectly");


    }

    @DisplayName("WHEN the provided range is invalid, THEN no array should be modified AND return false")
    @Test
    void testInvalidRange() {
        //Range is > length of array
        int[] arr1 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        int[] arr2 = {20, 21, 22, 23, 24};
        assertFalse(ArrayUtilities.copyRange(arr1, 0, arr2, 0, 6),
                "Does not return false when range is invalid");
        assertFalse(ArrayUtilities.copyRange(arr2, 0, arr1, 0, 6),
                "Does not return false when range is invalid");
        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8}, arr1,
                "Source array should not be modified");
        assertArrayEquals(new int[]{20, 21, 22, 23, 24}, arr2,
                "Destination array should not be modified");

        int[] arr3 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
        int[] arr4 = arr3;
        assertFalse(ArrayUtilities.copyRange(arr3, 0, arr4, 0, 10),
                "Does not return false when range is invalid");
        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8}, arr3,
                "Source array should not be modified");
        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8}, arr4,
                "Destination array should not be modified");

        //Range < 0
        int[] arr5 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] arr6 = {20, 21, 22, 23, 24};
        assertFalse(ArrayUtilities.copyRange(arr5, 0, arr6, 0, -1),
                "Does not return false when range is invalid");
        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, arr1,
                "Source array should not be modified");
        assertArrayEquals(new int[]{20, 21, 22, 23, 24}, arr2,
                "Destination array should not be modified");
    }

    @DisplayName("WHEN the start position of either array is invalid, THEN no array should be modified AND return false")
    @Test
    void testInvalidStartPos() {
        int[] arr1 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        int[] arr2 = {20, 21, 22, 23, 24};
        assertFalse(ArrayUtilities.copyRange(arr1, 0, arr2, 5, 1),
                "Does not return false when destination start position is invalid");
        assertFalse(ArrayUtilities.copyRange(arr1, 17, arr2, 0, 1),
                "Does not return false when source start position is invalid");
        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16}, arr1,
                "Source array should not be modified");
        assertArrayEquals(new int[]{20, 21, 22, 23, 24}, arr2,
                "Destination array should not be modified");

        int[] arr5 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] arr6 = {20, 21, 22, 23, 24};
        assertFalse(ArrayUtilities.copyRange(arr5, -1, arr6, 0, 2),
                "Does not return false when start pos is negative");
        assertFalse(ArrayUtilities.copyRange(arr5, 0, arr6, -1, 2),
                "Does not return false when start pos is negative");
        assertArrayEquals(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10}, arr5,
                "Source array should not be modified");
        assertArrayEquals(new int[]{20, 21, 22, 23, 24}, arr6,
                "Destination array should not be modified");
    }

    void testInvalidRangeStartPos(){
        //TODO when range and start position added are invalid, but individually are not invalid
    }
}
