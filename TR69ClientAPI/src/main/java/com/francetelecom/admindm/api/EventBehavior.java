/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : TR69ClientAPI
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
 */ 
package com.francetelecom.admindm.api;


/**
 * The Class EventBehavior.
 */
public final class EventBehavior {
    /** The retry policy. */
    private int retryPolicy;
    /** The count. */
    private int count = 0;
    /** The must be single. */
    private boolean mustBeSingle;
    /** The ACS Response for successful delivery. */
    private String successfulDelivery = "InformResponse";

    /**
     * Constructor.
     * @param pMustBeSingle the must be single
     * @param pRetryPolicy the retry policy
     * @param pSuccessfulDelivery the ACS Response for successful delivery
     */
    public EventBehavior(final boolean pMustBeSingle,
            final int pRetryPolicy, final String pSuccessfulDelivery) {
        this.mustBeSingle = pMustBeSingle;
        this.retryPolicy = pRetryPolicy;
        this.successfulDelivery = pSuccessfulDelivery;
    }

    /**
     * Checks if is must be single.
     * @return true, if is must be single
     */
    public boolean isMustBeSingle() {
        return mustBeSingle;
    }

    /**
     * Sets the must be single.
     * @param pMustBeSingle the new must be single
     */
    public void setMustBeSingle(final boolean pMustBeSingle) {
        this.mustBeSingle = pMustBeSingle;
    }

    /**
     * Gets the retry policy.
     * @return the retry policy
     */
    public int getRetryPolicy() {
        return retryPolicy;
    }

    /**
     * Sets the retry policy.
     * @param pRetryPolicy the new retry policy
     */
    public void setRetryPolicy(final int pRetryPolicy) {
        this.retryPolicy = pRetryPolicy;
    }

    /**
     * Gets the count.
     * @return the count
     */
    public int getCount() {
        return count;
    }

    /**
     * Sets the count.
     * @param pCount the new count
     */
    public void setCount(final int pCount) {
        this.count = pCount;
    }

    /**
     * Sets the successful delivery RPCMethod name who will acknowledge
     * this kind of event.
     * @param pSuccessfulDelivery the successful delivery
     */
    public void setSuccessfulDelivery(final String pSuccessfulDelivery) {
        this.successfulDelivery = pSuccessfulDelivery;
    }

    /**
     * Gets the successful delivery.
     * @return the successful delivery
     */
    public String getSuccessfulDelivery() {
        return successfulDelivery;
    }
}
