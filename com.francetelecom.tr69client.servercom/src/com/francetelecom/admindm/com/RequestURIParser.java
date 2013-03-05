/*--------------------------------------------------------
 * Product Name : modus TR-069
 * Version : 1.1
 * Module Name : ServerComBundle
 *
 * Copyright © 2011 France Telecom
 *
 * This software is distributed under the Apache License, Version 2.0
 * (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 or see the "license.txt" file for
 * more details
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author : Orange Labs R&D O.Beyler
 */
package com.francetelecom.admindm.com;

import java.util.Hashtable;

import com.francetelecom.admindm.api.Log;

/**
 * @author mpcy8647
 */
public class RequestURIParser {
	/** ts parameter name constant. */
	public static final String TS = "ts";
	/** id parameter name constant. */
	public static final String ID = "id";
	/** un parameter name constant. */
	public static final String UN = "un";
	/** cn parameter name constant. */
	public static final String CN = "cn";
	/** sig parameter name constant. */
	public static final String SIG = "sig";
	/** request uri as is. */
	private final String rawRequestUri;
	/** attributes. */
	private final Hashtable attributes;

	/**
	 * Initiates a RequestURIParser. The attributes of the RequestURI are stored
	 * in the hashtable.
	 * 
	 * @param requestUri
	 *            request uri string to parse
	 */
	public RequestURIParser(final String requestUri) {
		this.rawRequestUri = requestUri;
		this.attributes = new Hashtable();
		if (rawRequestUri != null) {
			// the request uri members are separated by ? charecters
			// String[] splittedRequestUri = rawRequestUri.split("\\?");
			String[] splittedRequestUri;
			try {
				splittedRequestUri = rawRequestUri.split("?");

			} catch (Exception e) {
				splittedRequestUri = null;
			}
			if (splittedRequestUri != null) {
				if (splittedRequestUri.length == 2) {
					String attributesString = splittedRequestUri[1];
					if (attributesString != null) {
						// the attribute are separated by "&" character
						// String[] splittedAttributes =
						// attributesString.split("&");
						String[] splittedAttributes;
						try {
							splittedAttributes = attributesString.split("&");
						} catch (Exception e) {
							splittedAttributes = null;
						}
						if (splittedAttributes != null) {
							for (int i = 0; i < splittedAttributes.length; i++) {
								// each splitted request uri member follows
								// parameter-name=parameter-value
								String splittedString = splittedAttributes[i];
								Log.debug("RequestURIParser splittedString = "
										+ splittedString);
								if (splittedString != null) {
									// String[] splittedRequestUriMember =
									// splittedString
									// .split("=");
									String[] splittedRequestUriMember;
									try {
										splittedRequestUriMember = splittedString.split("=");
									} catch (Exception e) {
										splittedRequestUriMember = null;
									}
									if (splittedRequestUri != null) {
										if (splittedRequestUriMember.length == 2) {
											attributes
													.put(
															splittedRequestUriMember[0],
															splittedRequestUriMember[1]);
										} else if (splittedRequestUriMember.length == 1) {
											attributes
													.put(
															splittedRequestUriMember[0],
															"");
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * <p>
	 * Get the value of an attribute.
	 * </p>
	 * 
	 * @param name
	 *            name
	 * @return value or null if the attribute is null or doesn't exist
	 */
	public final String getAttribute(final String name) {
		return (String) attributes.get(name);
	}
}
