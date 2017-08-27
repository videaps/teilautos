/*
 Copyright (c) 2017 Videa Project Services GmbH

 Permission is hereby granted, free of charge, to any person obtaining a copy of this software 
 and associated documentation files (the "Software"), to deal in the Software without restriction, 
 including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
 and/or sell copies of the Software,and to permit persons to whom the Software is furnished to do so, 
 subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial 
 portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT 
 NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE 
 SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package de.teilautos.registration;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.Expression;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IdentificationMailCreatorDelegate implements JavaDelegate {
	private final Logger logger = LoggerFactory.getLogger(IdentificationMailCreatorDelegate.class);

	private Expression firstname;

	public void execute(DelegateExecution execution) throws Exception {
		logger.info("entering");

		String firstnameValue = (String) firstname.getValue(execution);
		if (logger.isTraceEnabled()) {
			logger.trace("firstname="+firstnameValue);
		}
		
		String subject = "Deine Identifizierung für Teilautos - Das regionale Carsharing";
		String content = "Hallo " + firstnameValue
				+ " und herzlich Willkommen bei Teilautos. \n\n Vielen Dank, dass du dich bei uns angemeldet hast. Um deine Registrierung zu vervollständigen, folge bitte diesem Link zur Identifizierung. \n\n Jetzt mit PostIdent identifizieren: https://postident.deutschepost.de/identportal/wl?an=63006679423701 \n\n Hier kannst du ganz schnell und einfach deinen Führerschein und dein Personalausweis fotografieren und hochladen. \n\n Innerhalb von nur 15 Minuten erhältst du eine Bestätigung. Im Anschluss schicken wir dir deine Zugangskarte per Post zu - was dann voraussichtlich etwas länger dauern wird. \n\n\n Wir wünschen dir eine gute Fahrt. \n\n Dein Teilautos-Team aus Beckum \n\n Teilautos - Das regionale Carsharing \n Bergstraße 12 \n 59269 Beckum \n\n Telefon: 02521 821 3314 \n E-Mail: kontakt@teilautos.de \n Web: www.teilautos.de \n";

		execution.setVariable("identificationMailSubject", subject);
		execution.setVariable("identificationMailContent", content);

		if (logger.isTraceEnabled()) {
			logger.trace("identificationMailSubject=" + subject);
			logger.trace("identificationMailContent=" + content);
		}

		logger.info("exiting");
	}

}
