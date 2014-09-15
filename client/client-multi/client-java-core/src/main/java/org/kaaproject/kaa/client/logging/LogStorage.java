/*
 * Copyright 2014 CyberVision, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.kaaproject.kaa.client.logging;

/**
 * Interface for log storage.
 *
 * Persists each new log record, forms on demand
 * new log block for sending it to the operation server, removes already sent records,
 * cleans up elder records if case limitation set on a storage size. 
 *
 * Reference implementation used by default (@see MemoryLogStorage).
 */
public interface LogStorage {
    /**
     * Persists new log record.
     *
     * @param record New record (@see LogRecord)
     */
    void addLogRecord(LogRecord record);

    /**
     * Retrieves new log block of specified size or 
     * null if there is no logs. The size of retrieved log records 
     * should NOT be greater than specified block size.
     *
     * @param blockSize Maximum size of sending log block
     * @return New log block (@see LogBlock)
     */
    LogBlock getRecordBlock(long blockSize);

    /**
     * Removes already sent log records by its bloc id.
     *
     * @param id Unique id of sent log block
     */
    void removeRecordBlock(String id);

    /**
     * Removes records untill inner storage has equal or less size
     * than specified one.
     *
     * @param maximumAllowedVolume Maximum size of inner storage
     */
    void removeOldestRecord(long maximumAllowedVolume);

    /**
     * Notifies if sending of log block with specified id was failed.
     *
     * @param id Unique id of log block.
     */
    void notifyUploadFailed(String id);
}