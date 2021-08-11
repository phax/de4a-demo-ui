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

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * Test class for class {@link EDemoCanonicalEvidence}.
 *
 * @author Philip Helger
 */
public final class EDemoCanonicalEvidenceTest
{
  @Test
  public void testBasic ()
  {
    assertNotNull (EDemoCanonicalEvidence.createT41_UC1_v2021_02_11 ());
    assertNotNull (EDemoCanonicalEvidence.createT41_UC1_v2021_04_13 ());
    assertNotNull (EDemoCanonicalEvidence.createDBA_v06 ());
    assertNotNull (EDemoCanonicalEvidence.createMA_Birth_v16b ());
    assertNotNull (EDemoCanonicalEvidence.createMA_DomesticRegistration_v16b ());
    assertNotNull (EDemoCanonicalEvidence.createMA_Marriage_v16b ());
  }
}