/*
 * Sonar C# Plugin :: C# Squid :: Checks
 * Copyright (C) 2010 Jose Chillan, Alexandre Victoor and SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package com.sonar.csharp.checks;

import com.sonar.csharp.squid.scanner.CSharpAstScanner;
import org.sonar.squidbridge.checks.CheckMessagesVerifierRule;
import org.junit.Rule;
import org.junit.Test;
import org.sonar.squidbridge.api.SourceFile;

import java.io.File;

public class ClassCouplingCheckTest {

  @Rule
  public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

  @Test
  public void test() {
    SourceFile file = CSharpAstScanner.scanSingleFile(new File("src/test/resources/checks/classCoupling.cs"), new ClassCouplingCheck());

    checkMessagesVerifier.verify(file.getCheckMessages())
        .next().atLine(3).withMessage("Refactor this class that is coupled to 21 other classes (which is higher than 20 authorized).")
        .next().atLine(37).withMessage("Refactor this class that is coupled to 23 other classes (which is higher than 20 authorized).");
  }

  @Test
  public void custom() {
    ClassCouplingCheck check = new ClassCouplingCheck();
    check.couplingThreshold = 22;

    SourceFile file = CSharpAstScanner.scanSingleFile(new File("src/test/resources/checks/classCoupling.cs"), check);

    checkMessagesVerifier.verify(file.getCheckMessages())
        .next().atLine(37).withMessage("Refactor this class that is coupled to 23 other classes (which is higher than 22 authorized).");
  }

}
