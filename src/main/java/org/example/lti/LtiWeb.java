package org.example.lti;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import org.imsglobal.lti.launch.LtiVerificationResult;
import org.imsglobal.lti.launch.LtiOauthVerifier;
import org.imsglobal.lti.launch.LtiVerifier;

@Controller
public class LtiWeb {

	@RequestMapping(value = "/lti", method = RequestMethod.POST)
	public String ltiEntry(HttpServletRequest request) {
		try {
			LtiVerifier ltiVerifier = new LtiOauthVerifier();
			//String key = request.getParameter("oauth_consumer_key");
			String secret = "secret";
			LtiVerificationResult result = ltiVerifier.verify(request, secret);		
			if(result==null || !result.getSuccess()){
				return "error";
			} else {
				String mtype = request.getParameter("lti_message_type");
				if ("ContentItemSelectionRequest".equals(mtype)) {
					// Pensado para seleccionar un único elemento
					request.setAttribute("contentitems",
					"{"+
						"\"@context\":\"http://purl.imsglobal.org/ctx/lti/v1/ContentItem\","+
						"\"@graph\":["+
						   "{"+
							  "\"mediaType\":\"application/vnd.ims.lti.v1.ltilink\","+
							  "\"@type\":\"LtiLinkItem\","+
							   "\"title\":\"Deep link to video\","+ // ESTO ES LO QUE PONE EN EL TEXTO DEL LINK EN EL EDITOR
							  "\"custom\": {"+
									"\"video\": \"id1\","+ // ESTE ES EL ID DEL VIDEO QUE NECESITAS LUEGO
									"\"tool\": \"/lti/video.html\""+ // ESTO PUEDE SER LA PAGINA A LA QUE ACCEDE
						  	  	"}"+
							 "}]}");
					return "selectitem";
				} else {
					return "index";
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace(System.out);
			return "error";
		}
	}	

}