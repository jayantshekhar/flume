/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.flume.channel.jdbc;

import java.util.Properties;

import org.apache.flume.Channel;
import org.apache.flume.ChannelException;
import org.apache.flume.Event;
import org.apache.flume.Transaction;
import org.apache.log4j.Logger;

/**
 * <p>A JDBC based channel implementation.</p>
 */
public class JdbcChannel implements Channel {

  private static final Logger LOG = Logger.getLogger(JdbcChannel.class);

  private final JdbcChannelProvider provider;
  private final String name;

  /**
   * Instantiates a new JDBC Channel with the given properties.
   * @param configuration
   */
  public JdbcChannel(String name, Properties configuration) {
    provider = JdbcChannelProviderFactory.getProvider(configuration);
    this.name = name;

    LOG.info("JDBC Channel initialized: " + name);
  }

  @Override
  public void put(Event event) throws ChannelException {
    getProvider().persistEvent(getName(), event);
  }

  @Override
  public Event take() throws ChannelException {
    return getProvider().removeEvent(getName());
  }

  @Override
  public Transaction getTransaction() {
    return getProvider().getTransaction();
  }

  @Override
  public void shutdown() {
    // TODO Auto-generated method stub
  }

  @Override
  public String getName() {
    return name;
  }

  private JdbcChannelProvider getProvider() {
    return provider;
  }
}