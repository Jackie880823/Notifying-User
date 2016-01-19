/*
 * Copyright 2016 The Open Source Project of Jackie Zhu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jackie.notifyingUser;

/**
 * Created 16/1/19.
 * A set of constants used by all of the components in this application. To use these constants
 * the components implement the interface.
 *
 * @author Jackie
 * @version 1.0
 */
public final class CommonConstants {
    private CommonConstants() {
        // don't allow the class to be instantiated
    }

    public static final String ACTION_PING = "com.jackie.notifying.ACTION_PING";
    public static final String ACTION_DISMISS = "com.jackie.notifying.ACTION_DISMISS";
    public static final String ACTION_SNOOZE = "com.jackie.notifying.ACTION_SNOOZE";
    public static final String EXTRA_MESSAGE = "EXTRA_MESSAGE";
    public static final int NOTIFICATION_ID = 0x001;
}
