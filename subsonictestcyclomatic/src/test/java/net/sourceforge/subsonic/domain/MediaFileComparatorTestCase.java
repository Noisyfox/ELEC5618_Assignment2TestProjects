package net.sourceforge.subsonic.domain;

import junit.framework.TestCase;

public class MediaFileComparatorTestCase extends TestCase {

    private final MediaFileComparator comparator_1 = new MediaFileComparator(true);

    private final MediaFileComparator comparator_2 = new MediaFileComparator(false);

    // execute path: 1-2-15
    private boolean TC1() {
        MediaFile album1 = new MediaFile();
        album1.setMediaType(MediaFile.MediaType.MUSIC);

        MediaFile album2 = new MediaFile();
        album2.setMediaType(MediaFile.MediaType.DIRECTORY);

        return comparator_1.compare(album1, album2) == 1;
    }

    // execute path: 1-3-4-15
    private boolean TC2() {
        MediaFile album1 = new MediaFile();
        album1.setMediaType(MediaFile.MediaType.DIRECTORY);

        MediaFile album2 = new MediaFile();
        album2.setMediaType(MediaFile.MediaType.MUSIC);

        return comparator_1.compare(album1, album2) == -1;
    }

    // execute path: 1-3-5-2-15
    private boolean TC3() {
        MediaFile album1 = new MediaFile();
        album1.setMediaType(MediaFile.MediaType.ALBUM);

        MediaFile album2 = new MediaFile();
        album2.setMediaType(MediaFile.MediaType.DIRECTORY);

        return comparator_1.compare(album1, album2) == 1;
    }

    // execute path: 1-3-5-6-4-15
    private boolean TC4() {
        MediaFile album1 = new MediaFile();
        album1.setMediaType(MediaFile.MediaType.DIRECTORY);

        MediaFile album2 = new MediaFile();
        album2.setMediaType(MediaFile.MediaType.ALBUM);

        return comparator_1.compare(album1, album2) == -1;
    }

    // execute path: 1-3-5-6-7-8-9-15
    private boolean TC5() {
        MediaFile album1 = new MediaFile();
        album1.setMediaType(MediaFile.MediaType.ALBUM);
        album1.setYear(1990);

        MediaFile album2 = new MediaFile();
        album2.setMediaType(MediaFile.MediaType.ALBUM);
        album2.setYear(1991);

        return comparator_1.compare(album1, album2) != 0;
    }

    // execute path: 1-3-5-6-7-8-10-11-15
    private boolean TC6() {
        MediaFile album1 = new MediaFile();
        album1.setMediaType(MediaFile.MediaType.ALBUM);
        album1.setYear(1990);
        album1.setPath("album1");

        MediaFile album2 = new MediaFile();
        album2.setMediaType(MediaFile.MediaType.ALBUM);
        album2.setYear(1990);
        album2.setPath("album2");

        return comparator_1.compare(album1, album2) != 0;
    }

    // execute path: 1-3-5-6-7-10-12-13-15
    private boolean TC7() {
        MediaFile album1 = new MediaFile();
        album1.setMediaType(MediaFile.MediaType.MUSIC);
        album1.setTrackNumber(1);

        MediaFile album2 = new MediaFile();
        album2.setMediaType(MediaFile.MediaType.MUSIC);
        album2.setTrackNumber(2);

        return comparator_2.compare(album1, album2) != 0;
    }

    // execute path: 1-3-5-6-7-10-12-14-15
    private boolean TC8() {
        MediaFile album1 = new MediaFile();
        album1.setMediaType(MediaFile.MediaType.MUSIC);
        album1.setTrackNumber(1);
        album1.setPath("album1");

        MediaFile album2 = new MediaFile();
        album2.setMediaType(MediaFile.MediaType.MUSIC);
        album2.setTrackNumber(1);
        album2.setPath("album2");

        return comparator_2.compare(album1, album2) != 0;
    }

    // execute path: 1-3-5-6-7-10-11-15
    private boolean TC9() {
        MediaFile album1 = new MediaFile();
        album1.setMediaType(MediaFile.MediaType.DIRECTORY);
        album1.setPath("album1");

        MediaFile album2 = new MediaFile();
        album2.setMediaType(MediaFile.MediaType.DIRECTORY);
        album2.setPath("album2");

        return comparator_2.compare(album1, album2) != 0;
    }

    public void testCompareAlbums() {
        assertTrue(TC1());
        assertTrue(TC2());
        assertTrue(TC3());
        assertTrue(TC4());
        assertTrue(TC5());
        assertTrue(TC6());
        assertTrue(TC7());
        assertTrue(TC8());
        assertTrue(TC9());
    }

    public static void main(String[] arg) {
        new MediaFileComparatorTestCase().testCompareAlbums();
    }
}
