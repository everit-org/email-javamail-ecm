/*
 * Copyright (C) 2011 Everit Kft. (http://www.everit.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.everit.email.javamail.ecm;

/**
 * Constants for Java Mail Session Component.
 */
public final class JavamailSessionComponentConstants {

  /**
   * References of the component.
   */
  public static final class References {

    public static final String SERVICE_REF_AUTHENTICATOR = "authenticator";

    private References() {
    }
  }

  public static final String ATTR_AUTHENTICATOR_TARGET =
      References.SERVICE_REF_AUTHENTICATOR + ".target";

  public static final String ATTR_PROPERTIES = "properties";

  public static final String SERVICE_PID = "org.everit.email.javamail.ecm.Session";

  private JavamailSessionComponentConstants() {
  }
}
