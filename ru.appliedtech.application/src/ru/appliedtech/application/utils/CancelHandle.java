/*
* Copyright (c) 2013 Applied Technologies, Ltd.
*
* Permission is hereby granted, free of charge, to any person obtaining
* a copy of this software and associated documentation files (the
* "Software"), to deal in the Software without restriction, including
* without limitation the rights to use, copy, modify, merge, publish,
* distribute, sublicense, and/or sell copies of the Software, and to
* permit persons to whom the Software is furnished to do so, subject to
* the following conditions:
*
* The above copyright notice and this permission notice shall be
* included in all copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
* EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
* MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
* NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
* LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
* OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
* WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*
*/
package ru.appliedtech.application.utils;

/**
 * Implements long-time operation cancel logic
 */
public class CancelHandle
{
    private static class DummyHandle extends CancelHandle
    {
        public DummyHandle()
        {
            super(null);
        }
        
        public void cancel()
        {
        } 
        
        public void stopTargetThread()
        {
        }
    }
    
    public static CancelHandle DUMMY_HANDLE = new DummyHandle();
    
    /** Thread that runs long-time operation*/
    private final Thread m_targetTread;
    private boolean m_bCanceled;
    
    /**
     * Creates CancelHandle associated with the given thread;
     * Thread code should regulary check isCanceled() state of
     * the CancelHandle and stop execution quickly when it finds that
     * isCanceled state is on
     * @param targetTread
     */
    public CancelHandle(Thread targetTread)
    {
        m_targetTread = targetTread;  
        m_bCanceled = false;
    }
    
    /**
     * Creates CancelHandle associated with calling  thread;
     * Thread code should regulary check isCanceled() state of
     * the CancelHandle and stop execution quickly when it finds that
     * isCanceled state is on
     */
    public CancelHandle()
    {
        this(Thread.currentThread());  
    }
    
    /**
     * The results of this method should be regullary checked by the 
     * working thread that need to be cancelled 
     * @return
     */
    public final boolean isCanceled()
    {
        return m_bCanceled;
    }
    
    /**
     * Notifies working thread that it's time to cancel
     */
    public void cancel()
    {
        m_bCanceled = true;
    }
    
    /**
     * Notifies working thread that it's time to cancel
     * and waits until it stops
     */
    public void stopTargetThread()
    {
        m_bCanceled = true;
        try
        {
            m_targetTread.join();
        }
        catch(InterruptedException ignored)
        {            
        }
    }
    
}
