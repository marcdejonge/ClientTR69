/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : ServerComBundle
 *
 * Copyright © 2011 France Telecom
 *
 * This software is distributed under the Apache License, Version 2.0
 * (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 or see the "license.txt" file for
 * more details
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author : Orange Labs R&D O.Beyler
 */
package com.francetelecom.admindm.com;

/**
 * Implement a Semaphore to help thread synchronization.
 * @author Beyler Olivier FranceTelecom R&D
 */
public final class Semaphore {
    /**
     * internal count.
     */
    private int value;

    /**
     * Constructor.
     */
    public Semaphore() {
        super();
        value = 0;
    }

    /**
     * free the semaphore to restart all thread stopped on thread.
     */
    public void release() {
        synchronized (this) {
            ++value;
            // debloque les threads qui attendent cet evenement:
            notifyAll();
        }
    }

    /**
     * stop the current thread until an other release the semaphore.
     * @throws InterruptedException
     *             the interrupted exception
     */
    public void waiting() throws InterruptedException {
        waiting(0);
    }

    /**
     * stop the current thread until an other release the semaphore.
     * @param timeOut the time out
     * @throws InterruptedException the interrupted exception
     */
    public void waiting(final long timeOut) throws InterruptedException {
        synchronized (this) {
            while (value <= 0) {
                wait(timeOut); // bloque jusqu'a un notify()
            }
            value--;
        }
    }
}
