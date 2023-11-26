package com.integration.auth.common.util;

public class Constants {

	private Constants() {
	}

	public static final String BEARER = "BEARER";

	public static final String SESSION_TOKEN = "sessionToken";

	public static final String CLIENT_ID = "clientId";

	public static final String CLIENT_SECRET = "clientSecret";

	public static final String PASYSTEM = "paSystem";

	public static final String SOURCE_IP = "sourceIPAddress";

	public static final String DEVICE = "device";

	public static final String LOGIN_TYPE = "loginType";

	public static final String REFRESH_TOKEN = "refreshToken";

	public static final String LOGOUT_SUCCESSFUL = "Logout Successful";

	public static final String SESSION_INVALIDATED = "All active sessions are terminated for";

	public static final String USER_ID = "userId";

	public static final String AUTH_API_BASE_URI = "/v1/user/";

	public static final String LOGIN = "/login";

	public static final String SSO_LOGIN = "/sso/login";

	public static final String REFRESH = "/refresh";

	public static final String LOGOUT = "/logout";

	public static final String FORCE_LOGOUT = "/force/logout";

	public static final String VALIDATE = "/validate";

	public static final String MODULE_MAPPING = "/moduleMapping";

	public static final String REGISTER = "/register";

	public static final String MODIFY = "/modify";

	public static final String RESET_PASSWORD = "/resetPassword";

	public static final String DELETE = "/delete";

	public static final String IS_TERMINATED = "isTerminated";

	public static final String TERMINATION_REASON = "terminationReason";

	public static final String TERMINATED_BY_SYSTEM = "terminatedBySystem";

	public static final String TERMINATION_DATE = "terminationDate";

	public static final String LAST_ACCESSED = "lastAccessed";

	public static final String LAST_ACCESSED_BY_SYSTEM = "lastAccessedBySystem";

	public static final String SESSION_VALIDATED_SUCCESSFULLY = "Session Validated  successfully";

	public static final String MARKER = "integration-auth-service ";

	public static final String USER_REGISTERED_SUCCESSFULLY = "User registered successfully ";

	public static final String AUTH_FILTER_ERROR = "::AUTH FILTER ERROR: Failure while calling Mentioned API :: {} and Exception :: {}    ";

	public static final String LOGIN_API_ERROR = "::AUTH ERROR: Failure while calling login api  ";

	public static final String SSO_LOGIN_API_ERROR = "::AUTH ERROR: Failure while calling SSO login api  ";

	public static final String USER_ACTIVATION_ERROR = "::AUTH ERROR: Failure while changing user activation status";

	public static final String REFRESH_API_ERROR = " ::AUTH ERROR: Failure while calling refresh api  ";

	public static final String LOGOUT_API_ERROR = " ::AUTH ERROR: Failure while calling logout api  ";

	public static final String CHANGE_PASSWORD_API_ERROR = " ::AUTH ERROR: Failure while calling change password api  ";

	public static final String REGISTER_API_ERROR = " ::AUTH ERROR: Failure while calling Register api  ";

	public static final String VALIDATE_API_ERROR = " ::AUTH ERROR: Failure while calling Validate api  ";

	public static final String MODULE_MAPPING_API_ERROR = " ::AUTH ERROR: Failure while calling Module Mapping api  ";

	public static final String USERNAME = "username";

	public static final String USER_NAME = "userName";

	public static final String TENANT_ID = "tenantId";

	public static final String TENANT_NAME = "tenantName";

	public static final String TARGET_SYSTEM = "targetSystem";

	public static final String CREATION_DATE = "creation_date";

	public static final String JTI = "jti";

	public static final String USER_CREATED = "User created";

	public static final String USER_VALIDATED = "User got validated with provided token";

	public static final String NOT_APPLICABLE = "NA";

	public static final String REQUIRED_DATA_NOT_FOUND_OR_INVALID = "Required Data Not found OR Invalid ";

	public static final String FC_SYSTEM = "FC";

	public static final String WM_SYSTEM = "WM";

	public static final String CHANGE_PASSWORD = "/changePassword";

	public static final String ACTIVE_USER = "ACTIVE";

	public static final String ACTIVE = "active";

	public static final String PASSWORD_CHANGE = "Password Change Successfully";

	public static final String JSON_DATA = """
			{
			  "data": [
			    {
			      "baseUrlInfo": {
			        "baseUrl": "https://www.w3schools.com/"
			      },
			      "modules": [
			        {
			          "moduleInfo": {
			            "contextPath": "java/default.asp",
			            "displayName": "W3 School JAVA",
			            "isAccessible": true,
			            "sequence": 1
			          },
			          "subModules": []
			        },
			        {
			          "moduleInfo": {
			            "contextPath": "sql/default.asp",
			            "displayName": "W3 School SQL",
			            "isAccessible": true,
			            "sequence": 2
			          },
			          "subModules": [
			            {
			              "moduleInfo": {
			                "contextPath": "sql/sql_intro.asp",
			                "displayName": "SQL Intro",
			                "isAccessible": true,
			                "sequence": 1
			              },
			              "subModules": []
			            },
			            {
			              "moduleInfo": {
			                "contextPath": "sql/sql_intro.asp",
			                "displayName": "SQL Select",
			                "isAccessible": true,
			                "sequence": 2
			              },
			              "subModules": []
			            },
			            {
			              "moduleInfo": {
			                "contextPath": "sql/sql_orderby.asp",
			                "displayName": "SQL Order By",
			                "isAccessible": false,
			                "sequence": 3
			              },
			              "subModules": []
			            }
			          ]
			        },
			        {
			          "moduleInfo": {
			            "contextPath": "css/default.asp",
			            "displayName": "W3 CSS",
			            "isAccessible": true,
			            "sequence": 3
			          },
			          "subModules": [
			            {
			              "moduleInfo": {
			                "contextPath": "css/css_colors.asp",
			                "displayName": "CSS Color",
			                "isAccessible": true,
			                "sequence": 1
			              },
			              "subModules": []
			            },
			            {
			              "moduleInfo": {
			                "contextPath": "css/css_background.asp",
			                "displayName": "CSS Background",
			                "isAccessible": false,
			                "sequence": 2
			              },
			              "subModules": []
			            },
			            {
			              "moduleInfo": {
			                "contextPath": "NA",
			                "displayName": "CSS Background Theme",
			                "isAccessible": false,
			                "sequence": 0
			              },
			              "subModules": []
			            },
			            {
			              "moduleInfo": {
			                "contextPath": "css/scs.avx",
			                "displayName": "NA",
			                "isAccessible": false,
			                "sequence": 0
			              },
			              "subModules": []
			            },
			            {
			              "moduleInfo": {
			                "contextPath": "NA",
			                "displayName": "NA",
			                "isAccessible": true,
			                "sequence": 0
			              },
			              "subModules": []
			            }
			          ]
			        },
			        {
			          "moduleInfo": {
			            "contextPath": "NA",
			            "displayName": "W3 Marketing",
			            "isAccessible": true,
			            "sequence": 4
			          },
			          "subModules": [
			            {
			              "moduleInfo": {
			                "contextPath": "bootstrap/bootstrap_ver.asp",
			                "displayName": "Dashboard",
			                "isAccessible": true,
			                "sequence": 1
			              },
			              "subModules": []
			            },
			            {
			              "moduleInfo": {
			                "contextPath": "react/default.asp",
			                "displayName": "CRM",
			                "isAccessible": true,
			                "sequence": 2
			              },
			              "subModules": [
			                {
			                  "moduleInfo": {
			                    "contextPath": "cpp/default.asp",
			                    "displayName": "Home",
			                    "isAccessible": true,
			                    "sequence": 1
			                  },
			                  "subModules": []
			                },
			                {
			                  "moduleInfo": {
			                    "contextPath": "mysql/default.asp",
			                    "displayName": "Leads",
			                    "isAccessible": true,
			                    "sequence": 2
			                  },
			                  "subModules": []
			                },
			                {
			                  "moduleInfo": {
			                    "contextPath": "NA",
			                    "displayName": "Accounts",
			                    "isAccessible": false,
			                    "sequence": 3
			                  },
			                  "subModules": []
			                },
			                {
			                  "moduleInfo": {
			                    "contextPath": "excel/index.php",
			                    "displayName": "Contacts",
			                    "isAccessible": true,
			                    "sequence": 4
			                  },
			                  "subModules": []
			                },
			                {
			                  "moduleInfo": {
			                    "contextPath": "xml/default.asp",
			                    "displayName": "Opportunities",
			                    "isAccessible": true,
			                    "sequence": 5
			                  },
			                  "subModules": []
			                },
			                {
			                  "moduleInfo": {
			                    "contextPath": "django/index.php",
			                    "displayName": "Groups",
			                    "isAccessible": true,
			                    "sequence": 6
			                  },
			                  "subModules": []
			                },
			                {
			                  "moduleInfo": {
			                    "contextPath": "nexturl?nextUrl=cmSearchForm&menuName=cm&subMenuName=Search&subMenuURL=cmSearchForm",
			                    "displayName": "Search",
			                    "isAccessible": true,
			                    "sequence": 7
			                  },
			                  "subModules": []
			                },
			                {
			                  "moduleInfo": {
			                    "contextPath": "nexturl?nextUrl=cmCampaignSummary&menuName=cm&subMenuName=cmCamapign",
			                    "displayName": "Campaign Center",
			                    "isAccessible": true,
			                    "sequence": 8
			                  },
			                  "subModules": []
			                }
			              ]
			            }
			          ]
			        }
			      ],
			      "userInfo": {
			        "tenantName": "wmi1",
			        "userName": "001_dev_5thoct_corp_01",
			        "paSystem": "FC",
			        "tenantId": "wmi1.franconnectdev.com"
			      }
			    }
			  ]
			}
			""";

	public static final String Y = "Y";

	public static final String N = "N";

}