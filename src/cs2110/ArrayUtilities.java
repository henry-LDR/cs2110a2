package cs2110;

/**
 * Contains utility methods for copying an array range to another array range (potentially within
 * the same array).
 */
public class ArrayUtilities {

    /**
     *
     * @param array: Array from which a copy of a range will be taken
     * @param start: Starting index of the range (inclusive)
     * @param end:   Ending index of the range (exclusive)
     * @return: Returns an int[] range, a new array object that contains the elements in
     * range[start,end) of array. Requires start < end, array != null
     */
    static int[] copyOfRange(int[] array, int start, int end) {
        //Check pre-conditions
        assert (array != null);
        assert (start <= end);
        //Initialize variables
        int len = end - start;
        int[] range = new int[len];
        //Do the copying
        for (int i = 0; i < len; i++) {
            range[i] = array[start + i];
        }
        //Return array
        return range;
    }

    /**
     * //TODO should also describe preconditions?
     *
     * @param src:      The array containing the value to be copied
     * @param srcStart: The index of the first element to copy
     * @param dst:      The array to which the range will be copied
     * @param dstStart: The index of the first location where the copied data wil be written
     * @param length:   The number of elements to be copied
     * @return True if the inputs are valid, and will also perform the copying. False if the inputs
     * are invalid and no modifications are made. Returns false values for srcStart, dstStart, or
     * length if an ArrayOutOfBoundException is thrown while performing a range copy. If the
     * specified range of values to be copied exceeds the size of the src or dst arrays, returns
     * false. That is, if dstStart + length > dst.length or srcStart + length > src.length. If the
     * starting point of the src or dst array is out of bounds, returns false. That is, if 0 >
     * srcStart or 0 > dstStart. If length < 0, then also return false. Requires that src and dst
     * are not null, src.length > 0, and dst.length >0,
     */
    static boolean copyRange(int[] src, int srcStart, int[] dst, int dstStart, int length) {
        //Check preconditions
        assert (src != null);
        assert (dst != null);
        assert (src.length > 0);
        assert (dst.length > 0);
        //If length = 0, then we don't need to even access any arrays and nothing happens,
        //so just return true automatically
        if (length == 0) {
            return true;
        }
        //Case of false: All conditions that could trigger an output of false
        if (length < 0 || srcStart < 0 || dstStart < 0 || dstStart + length > dst.length
                || srcStart + length > src.length) {
            return false;
        }
        //Otherwise, do the copying
        else {
            int[] range = copyOfRange(src, srcStart, srcStart + length);
            //Loop invariant: 0<= dstStart+i < dst.length
            for (int i = 0; i < range.length; i++) {
                dst[dstStart + i] = range[i];
            }
            return true;
        }
    }

    /**
     * //TODO preconditions?
     *
     * @param src:    The 2D array containing the array to be copied
     * @param srcI:   The first "outer array" index of the range to copy
     * @param srcJ:   The first "inner array" index of the range to copy
     * @param dst:    The 2D array to which the range will be copied
     * @param dstI:   The "outer array" index of the first location where the copied data will be
     *                written
     * @param dstJ:   The "inner array" index of the first location where the copied data will be
     *                written
     * @param height: The number of "outer array" indices over which the copied range spans
     * @param width:  The number of "inner array" indices over which the copied range spans
     * @return True if the inputs are valid, and will then also copy over src array elements into
     * dst array. True if height AND width == 0. False if height, width, srcI, srcJ, dstI, or dstJ
     * are negative, or if srcI+height>src.length, srcJ+width > src[i].length (for each i),
     * dstI+height>dst.length, dstJ+width>dst[i].length (for each i).
     */
    // TODO 6a: Write JavaDoc specifications for this method based on the description of its behavior
    //  in the assignment handout.
    static boolean copy2DRange(int[][] src, int srcI, int srcJ, int[][] dst, int dstI, int dstJ,
            int height, int width) {
        //Check preconditions
        assert (src != null);
        assert (dst != null);
        assert (src.length > 0);
        assert (dst.length > 0);
        //If the height or width is 0, return true automatically and don't do anything since this is always valid
        //as we're doing nothing
        if (height == 0 || width == 0) {
            return true;
        }
        //Case of false:
        if (height < 0 || width < 0 || srcI < 0 || srcJ < 0 || dstI < 0 || dstJ < 0) {
            return false;
        }
        // TODO 6b: Complete the definition of this method. You may not call any methods outside of
        //  the `ArrayUtilities` class, and you must document the invariants of any loop(s) with a
        //  comment.
        assert (src != null);
        assert (dst != null);
        //If height and width both equal 0, then we don't need to even access any arrays and nothing happens,
        //so just return true automatically
        if (height == 0 || width == 0) {
            return true;
        }

        //Case of false: All conditions that could trigger an output of false,
        // except for when srcJ+width > src[i].length or dstJ+width>dst[i].length (for each i).
        // This case is handled by calling the copyRange() method, which will return false
        // if the array is too short
        if (height < 0 || width < 0 || srcI < 0 || srcJ < 0 || dstI < 0 || dstJ < 0
                || srcI + height > src.length
                || dstI + height > dst.length) {
            return false;
        }

        //copy range into a temporary 2D array
        int[][] temp2DArray = new int[height][width];
        for (int i = 0; i < height; i++) {
            if(!copyRange(src[srcI+i], srcJ, temp2DArray[i], 0, width)){
                return false;
            }
        }

        //copy temporary range into dst array
        for(int i = 0; i<height;i++){
            if(!copyRange(temp2DArray[i], 0, dst[i+dstI], dstJ, width)){
                return false;
            }
        }
        return true;


    }


}
