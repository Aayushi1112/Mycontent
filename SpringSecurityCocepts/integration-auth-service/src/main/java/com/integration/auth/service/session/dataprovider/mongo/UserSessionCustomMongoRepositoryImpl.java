package com.integration.auth.service.session.dataprovider.mongo;

import com.integration.auth.common.errors.ErrorCodesAndMessages;
import com.integration.auth.common.errors.Errors;
import com.integration.auth.common.exceptions.InvalidTokenException;
import com.integration.auth.common.util.CommonUtil;
import com.integration.auth.common.util.Constants;
import com.integration.auth.common.util.DateTimeManager;
import com.integration.auth.common.util.TargetTypes;
import com.integration.auth.service.session.domain.UserSessionDetails;
import com.mongodb.client.result.UpdateResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class UserSessionCustomMongoRepositoryImpl implements UserSessionCustomMongoRepository {

	@Autowired
	CommonUtil commonUtil;

	private final MongoTemplate mongoTemplate;

	public UserSessionCustomMongoRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public void terminateSession(String sessionToken) {
		log.info(Constants.MARKER + " :: Calling Mongo Custom Method terminateSession Method Starts");

		Query query = Query.query(new Criteria().andOperator(Criteria.where(Constants.SESSION_TOKEN).is(sessionToken),
				Criteria.where(Constants.IS_TERMINATED).is(false)));
		Update update = new Update();
		update.set(Constants.IS_TERMINATED, true);
		update.set(Constants.LAST_ACCESSED, DateTimeManager.currentTimeStampDateInUTC());
		update.set(Constants.TERMINATION_REASON, "Self_Logout");
		update.set(Constants.TERMINATION_DATE, DateTimeManager.currentTimeStampDateInUTC());
		update.set(Constants.TERMINATED_BY_SYSTEM, commonUtil.getPaSystem());
		update.set(Constants.LAST_ACCESSED_BY_SYSTEM, commonUtil.getPaSystem());
		UpdateResult updateResult = mongoTemplate.updateFirst(query, update, UserSessionDetails.class);

		if (updateResult.getModifiedCount() > 0) {
			log.info("Session terminated Successfully for sharedSessionToken");
		}
		else {
			throw new InvalidTokenException(new Exception("Invalid Token"),
					new Errors(TargetTypes.TERMINATE_SESSION.name(), ErrorCodesAndMessages.INVALID_TOKEN,
							ErrorCodesAndMessages.INVALID_TOKEN_CODE));
		}
		log.info(Constants.MARKER + " :: Calling Mongo Custom Method terminateSession Method Ends");
	}

	@Override
	public UserSessionDetails refreshSession(String generatedSessionToken, String generatedRefreshToken,
			String existingRefreshToken) {
		log.info(Constants.MARKER + " :: Calling Mongo Custom Method refreshSession Method Starts");

		Query query = Query.query(Criteria.where(Constants.REFRESH_TOKEN).is(existingRefreshToken));
		UserSessionDetails existingSession = mongoTemplate.findOne(query, UserSessionDetails.class);

		if (existingSession != null && !existingSession.isTerminated()) {
			Update update = new Update();
			update.set(Constants.IS_TERMINATED, true);
			update.set(Constants.LAST_ACCESSED, DateTimeManager.currentTimeStampDateInUTC());
			update.set(Constants.TERMINATION_REASON, "Session Timeout");
			update.set(Constants.TERMINATED_BY_SYSTEM, commonUtil.getPaSystem());
			update.set(Constants.TERMINATION_DATE, DateTimeManager.currentTimeStampDateInUTC());
			update.set(Constants.LAST_ACCESSED_BY_SYSTEM, commonUtil.getPaSystem());
			mongoTemplate.updateFirst(query, update, UserSessionDetails.class);
			return existingSession;
		}
		else {
			log.error(Constants.MARKER + " :: No session found for existing refresh token {} ", existingRefreshToken);
			throw new InvalidTokenException(new Exception("Invalid Token"), new Errors(TargetTypes.REFRESH_TOKEN.name(),
					ErrorCodesAndMessages.INVALID_TOKEN, ErrorCodesAndMessages.INVALID_TOKEN_CODE));
		}
	}

}
