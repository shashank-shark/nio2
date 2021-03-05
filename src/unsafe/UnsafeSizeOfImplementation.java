package unsafe;

import sun.misc.Unsafe;

public class UnsafeSizeOfImplementation {

    public static void main(String[] args) {
        SimpleStructure simpleStructure = new SimpleStructure();
        simpleStructure.x = 100;
        long size = UnsafeUtils.sizeOf(simpleStructure);
        System.out.println("Size of SimpleStrucure Object with [x]: " + size);
        simpleStructure.y = 200;
        size = UnsafeUtils.sizeOf(SimpleStructure.class);
        System.out.println("Size of SimpleStructure Object with [x,y]: " + size);
        Unsafe unsafe = UnsafeUtils.getUnsafe();

    }
}
