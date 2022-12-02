import com.liferay.chess.model.ChessGame;
import com.liferay.chess.service.ChessGameLocalServiceUtil;

import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;

Long companyId = CompanyThreadLocal.getCompanyId();

long whitePlayerUserId = UserLocalServiceUtil.getUserIdByEmailAddress(
		companyId, "ruben@chess.com");

long blackPlayerUserId = UserLocalServiceUtil.getUserIdByEmailAddress(
		companyId, "richi@chess.com");

Group group = GroupLocalServiceUtil.getUserGroup(
		companyId, whitePlayerUserId);

ChessGame chessGame = ChessGameLocalServiceUtil.addChessGame(
		whitePlayerUserId, group.getGroupId(), whitePlayerUserId,
		blackPlayerUserId, new ServiceContext());

out.println(chessGame.getChessGameId());