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
package eu.de4a.demoui.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.helger.commons.ValueEnforcer;
import com.helger.commons.annotation.UsedViaReflection;
import com.helger.commons.collection.impl.CommonsHashMap;
import com.helger.commons.collection.impl.ICommonsMap;
import com.helger.commons.string.StringHelper;
import com.helger.scope.singleton.AbstractGlobalSingleton;

import eu.de4a.iem.core.jaxb.common.RedirectUserType;
import eu.de4a.iem.core.jaxb.common.ResponseExtractMultiEvidenceType;

/**
 * This class contains all instances of the {@link RedirectUserType}.
 * This stores the Post-Redirect-Get response for the USI-back-redirection.
 *
 * @author Philip Helger
 */
public final class EvidenceResponseMap extends AbstractGlobalSingleton
{
  private static final Logger LOGGER = LoggerFactory.getLogger (EvidenceResponseMap.class);
  private final ICommonsMap <String, ResponseExtractMultiEvidenceType> m_aMap = new CommonsHashMap <> ();

  @Deprecated
  @UsedViaReflection
  public EvidenceResponseMap ()
  {}

  @Nonnull
  public static EvidenceResponseMap getInstance ()
  {
    return getGlobalSingleton (EvidenceResponseMap.class);
  }

  public void register (@Nonnull final ResponseExtractMultiEvidenceType aResponse)
  {
    ValueEnforcer.notNull (aResponse, "Response");
    final String sKey = aResponse.getRequestId();
    m_aRWLock.writeLocked ( () -> {
      if (m_aMap.containsKey (sKey))
        LOGGER.warn ("Overwriting Evidence for '" + sKey + "'");
      m_aMap.put (sKey, aResponse);
    });
  }

  @Nullable
  public ResponseExtractMultiEvidenceType get (@Nullable final String sID)
  {
    if (StringHelper.hasNoText (sID))
      return null;
    return m_aRWLock.readLockedGet ( () -> m_aMap.get (sID));
  }

  @Nullable
  public ResponseExtractMultiEvidenceType getAndRemove (@Nullable final String sID)
  {
    if (StringHelper.hasNoText (sID))
      return null;
    return m_aRWLock.writeLockedGet ( () -> m_aMap.remove (sID));
  }

	public ICommonsMap<String, ResponseExtractMultiEvidenceType> getM_aMap() {
		return m_aMap;
	}
}