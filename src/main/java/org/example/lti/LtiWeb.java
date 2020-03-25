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