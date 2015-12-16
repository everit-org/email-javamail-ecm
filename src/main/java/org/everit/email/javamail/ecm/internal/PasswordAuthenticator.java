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
package org.everit.email.javamail.ecm.internal;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Simple class that can authenticate with he passed {@link PasswordAuthentication} instance.
 */
public class PasswordAuthenticator extends Authenticator {

  private PasswordAuthentication passwordauthentication;

  public PasswordAuthenticator(final PasswordAuthentication passwordauthentication) {
    this.passwordauthentication = passwordauthentication;
  }

  @Override
  protected PasswordAuthentication getPasswordAuthentication() {
    return passwordauthentication;
  }

  public void setPasswordauthentication(final PasswordAuthentication passwordauthentication) {
    this.passwordauthentication = passwordauthentication;
  }

}
