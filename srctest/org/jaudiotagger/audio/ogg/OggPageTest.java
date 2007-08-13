package org.jaudiotagger.audio.ogg;

import junit.framework.TestCase;

import java.io.File;
import java.io.RandomAccessFile;

import org.jaudiotagger.AbstractTestCase;
import org.jaudiotagger.tag.vorbiscomment.VorbisCommentTag;
import org.jaudiotagger.audio.ogg.util.OggPageHeader;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;

/**
 * Basic Vorbis tests
 */
public class OggPageTest extends TestCase
{
    /**
     * test Read Ogg Pages ok
     */
    public void testReadAllOggPages()
    {
        Exception exceptionCaught = null;
        int count = 0;
        try
        {
            File testFile = AbstractTestCase.copyAudioToTmp("test.ogg",new File("testReadAllOggPages.ogg"));
            RandomAccessFile raf = new RandomAccessFile(testFile,"r");

            OggPageHeader lastPageHeader = null;
            while(raf.getFilePointer()<raf.length())
            {

                System.out.println("pageHeader starts at:"+raf.getFilePointer());
                OggPageHeader pageHeader = OggPageHeader.read (raf);
                if(lastPageHeader!=null)
                {
                    assertEquals(lastPageHeader.getPageSequence()+1,pageHeader.getPageSequence());
                }
                System.out.println("pageHeader finishes at:"+raf.getFilePointer());
                System.out.println(pageHeader+"\n");
                raf.seek(raf.getFilePointer() + pageHeader.getPageLength());
                count++;
                lastPageHeader=pageHeader;

            }
            assertEquals(raf.length(),raf.getFilePointer());

        }
        catch(Exception e)
        {
             e.printStackTrace();
             exceptionCaught = e;
        }
        assertNull(exceptionCaught);
        assertEquals(10,count);
    }

    /**
     * test Read Ogg Pages ok
     */
    public void testReadAllOggPagesLargeFile()
    {
        Exception exceptionCaught = null;
        int count = 0;
        try
        {
            File testFile = AbstractTestCase.copyAudioToTmp("testlargeimage.ogg",new File("testReadAllOggPagesLargeFile.ogg"));
            RandomAccessFile raf = new RandomAccessFile(testFile,"r");


            while(raf.getFilePointer()<raf.length())
            {
                System.out.println("pageHeader starts at:"+raf.getFilePointer());
                OggPageHeader pageHeader = OggPageHeader.read (raf);
                System.out.println("pageHeader finishes at:"+raf.getFilePointer());
                System.out.println(pageHeader+"\n");
                raf.seek(raf.getFilePointer() + pageHeader.getPageLength());
                count++;
            }
            assertEquals(raf.length(),raf.getFilePointer());

        }
        catch(Exception e)
        {
             e.printStackTrace();
             exceptionCaught = e;
        }
        assertNull(exceptionCaught);
        assertEquals(25,count);
    }

}
