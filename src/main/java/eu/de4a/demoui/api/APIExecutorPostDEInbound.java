/**
 * Copyright (C) 2021 DE4A
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
package eu.de4a.demoui.api;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.pdfbox.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import com.helger.commons.annotation.Nonempty;
import com.helger.commons.http.CHttp;
import com.helger.commons.io.stream.StreamHelper;
import com.helger.photon.api.IAPIDescriptor;
import com.helger.photon.api.IAPIExecutor;
import com.helger.servlet.response.UnifiedResponse;
import com.helger.web.scope.IRequestWebScopeWithoutResponse;

import eu.de4a.demoui.AppConfig;
import eu.de4a.iem.core.DE4ACoreMarshaller;
import eu.de4a.iem.core.IDE4ACanonicalEvidenceType;
import eu.de4a.iem.core.jaxb.common.RequestExtractMultiEvidenceIMType;
import eu.de4a.iem.core.jaxb.common.ResponseErrorType;
import eu.de4a.iem.core.jaxb.common.ResponseEventSubscriptionType;
import eu.de4a.iem.core.jaxb.common.ResponseExtractMultiEvidenceType;

public class APIExecutorPostDEInbound implements IAPIExecutor
{
  private static final Logger LOGGER = LoggerFactory.getLogger (APIExecutorPostDEInbound.class);

  public void invokeAPI (@Nonnull final IAPIDescriptor aAPIDescriptor,
                         @Nonnull @Nonempty final String sPath,
                         @Nonnull final Map <String, String> aPathVariables,
                         @Nonnull final IRequestWebScopeWithoutResponse aRequestScope,
                         @Nonnull final UnifiedResponse aUnifiedResponse) throws Exception
  {
    LOGGER.info ("Received inbound DE4A message");

    final byte [] aPayload = StreamHelper.getAllBytes (aRequestScope.getRequest ().getInputStream ());
    LOGGER.info ("Received " + aPayload.length + " bytes");

    // TODO handle
    
    //MARSHALLING
    DE4ACoreMarshaller<ResponseExtractMultiEvidenceType> marshaller = DE4ACoreMarshaller.deResponseExtractMultiEvidenceMarshaller(IDE4ACanonicalEvidenceType.NONE);
    InputStream targetStream = new ByteArrayInputStream(aPayload);
    ResponseExtractMultiEvidenceType res = marshaller.read(targetStream);
    ResponseExtractMultiEvidenceType res2 = marshaller.read(aPayload);
    LOGGER.info ("Unmarshalled payload");
    
    //SAVE TO FILE
    LOGGER.info ("Saving evidence file  " + AppConfig.getDEXmlWriteTo () );
    File targetFile = new File(AppConfig.getDEXmlWriteTo ());
    OutputStream outStream = new FileOutputStream(targetFile);
    outStream.write(aPayload);
    IOUtils.closeQuietly(outStream);
    
    aUnifiedResponse.disableCaching ();
    aUnifiedResponse.setStatus (CHttp.HTTP_NO_CONTENT);

    LOGGER.info ("Finished handling inbound DE4A message");
  }
  
}
