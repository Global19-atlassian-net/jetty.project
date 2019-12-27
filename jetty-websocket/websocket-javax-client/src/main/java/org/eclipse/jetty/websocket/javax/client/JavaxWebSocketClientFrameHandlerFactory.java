//
//  ========================================================================
//  Copyright (c) 1995-2019 Mort Bay Consulting Pty. Ltd.
//  ------------------------------------------------------------------------
//  All rights reserved. This program and the accompanying materials
//  are made available under the terms of the Eclipse Public License v1.0
//  and Apache License v2.0 which accompanies this distribution.
//
//      The Eclipse Public License is available at
//      http://www.eclipse.org/legal/epl-v10.html
//
//      The Apache License v2.0 is available at
//      http://www.opensource.org/licenses/apache2.0.php
//
//  You may elect to redistribute this code under either of these licenses.
//  ========================================================================
//

package org.eclipse.jetty.websocket.javax.client;

import javax.websocket.ClientEndpoint;
import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;

import org.eclipse.jetty.websocket.javax.common.JavaxWebSocketContainer;
import org.eclipse.jetty.websocket.javax.common.JavaxWebSocketFrameHandlerFactory;
import org.eclipse.jetty.websocket.javax.common.JavaxWebSocketFrameHandlerMetadata;
import org.eclipse.jetty.websocket.javax.common.util.InvokerUtils;

public class JavaxWebSocketClientFrameHandlerFactory extends JavaxWebSocketFrameHandlerFactory
{
    public JavaxWebSocketClientFrameHandlerFactory(JavaxWebSocketContainer container, InvokerUtils.ParamIdentifier paramIdentifier)
    {
        super(container, paramIdentifier);
    }

    public JavaxWebSocketClientFrameHandlerFactory(JavaxWebSocketContainer container)
    {
        super(container, InvokerUtils.PARAM_IDENTITY);
    }

    @Override
    public EndpointConfig newDefaultEndpointConfig(Class<?> endpointClass, String path)
    {
        return new BasicClientEndpointConfig();
    }

    @Override
    public JavaxWebSocketFrameHandlerMetadata createMetadata(Class<?> endpointClass, EndpointConfig endpointConfig)
    {
        if (javax.websocket.Endpoint.class.isAssignableFrom(endpointClass))
        {
            return createEndpointMetadata((Class<? extends Endpoint>)endpointClass, endpointConfig);
        }

        if (endpointClass.getAnnotation(ClientEndpoint.class) == null)
        {
            return null;
        }

        JavaxWebSocketFrameHandlerMetadata metadata = new JavaxWebSocketFrameHandlerMetadata(endpointConfig);
        return discoverJavaxFrameHandlerMetadata(endpointClass, metadata);
    }
}