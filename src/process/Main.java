package process;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Main implements FileCode{

	public static void main(String[] args){
		File file = new File(args[0]);
		BufferedImage img = null;
		try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		img = removeStamp(img);
		switch (Receipt.check(img)) {
		case cost:
			writeFile(args[1], cost);
			System.out.println(file.getName() + "——缴纳诉讼费");
			break;
		case proofOfService:
			writeFile(args[1], proofOfService);
			System.out.println(file.getName() + "——送达回证");
			break;
		default:
			String result = Document.check(img);
			System.out.println(result);
			switch (result) {
			case evidence:
				writeFile(args[1], evidence);
				System.out.println(file.getName() + "——证据");
				break;
			case indictment:
				writeFile(args[1], indictment);
				System.out.println(file.getName() + "——起诉书");
				break;
			case indictFront:
				writeFile(args[1], indictment);
				System.out.println(file.getName() + "——起诉书首页");
				break;
			case guarantee:
				writeFile(args[1], guarantee);
				System.out.println(file.getName() + "——保证书、担保书");
				break;
			case answer:
				writeFile(args[1], answer);
				System.out.println(file.getName() + "——答辩状");
				break;
			case answerFront:
				writeFile(args[1], answerFront);
				System.out.println(file.getName() + "——答辩状首页");
				break;
			case identification:
				writeFile(args[1], identification);
				System.out.println(file.getName() + "——授权委托书、身份证明");
				break;
			case end:
				writeFile(args[1], end);
				System.out.println(file.getName() + "——文件尾页");
				break;
			case representative:
				writeFile(args[1], representative);
				System.out.println(file.getName() + "——代理词");
				break;
			case representativeFront:
				writeFile(args[1], representative);
				System.out.println(file.getName() + "——首页-代理词");
				break;
			case execution:
				writeFile(args[1], execution);
				System.out.println(file.getName() + "——申请执行书");
				break;
			case executionFront:
				writeFile(args[1], executionFront);
				System.out.println(file.getName() + "——申请执行首页");
				break;
			case penitence:
				writeFile(args[1], penitence);
				System.out.println(file.getName() + "——悔过书");
				break;
			case penitenceFront:
				writeFile(args[1], penitenceFront);
				System.out.println(file.getName() + "——悔过书首页");
				break;
			case compromise:
				writeFile(args[1], compromise);
				System.out.println(file.getName() + "——和解协议");
				break;
			case stateOfPubProsecution:
				writeFile(args[1], stateOfPubProsecution);
				System.out.println(file.getName() + "——公诉词");
				break;
			case stateOfPubProsecutionFront:
				writeFile(args[1], stateOfPubProsecutionFront);
				System.out.println(file.getName() + "——首页——公诉词");
				break;
			case supersedeas:
				writeFile(args[1], supersedeas);
				System.out.println(file.getName() + "——撤诉书");
				break;
			case supersedeasFront:
				writeFile(args[1], supersedeasFront);
				System.out.println(file.getName() + "——撤诉书首页");
				break;
			case protestFront:
				writeFile(args[1], protestFront);
				System.out.println(file.getName() + "——抗诉书首页");
				break;
			case protest:
				writeFile(args[1], protest);
				System.out.println(file.getName() + "——抗诉书、上诉书");
				break;
			case shift:
				writeFile(args[1], shift);
				System.out.println(file.getName() + "——换押票");
				break;
			case judgement:
				writeFile(args[1], judgement);
				System.out.println(file.getName() + "——判决书");
				break;
			case judgementFront:
				writeFile(args[1], judgementFront);
				System.out.println(file.getName() + "——判决书首页");
				break;
			case agentRecommendation:
				writeFile(args[1], agentRecommendation);
				System.out.println(file.getName() + "——诉讼代理人推荐函");
				break;
			case witnessIncourtApplication:
				writeFile(args[1], witnessIncourtApplication);
				System.out.println(file.getName() + "——证人出庭申请书");
				break;
			case entrustJudge:
				writeFile(args[1], entrustJudge);
				System.out.println(file.getName() + "——委托宣判函");
				break;
			case reJudge:
				writeFile(args[1], reJudge);
				System.out.println(file.getName() + "——再审");
				break;
			case reJudgeFront:
				writeFile(args[1], reJudgeFront);
				System.out.println(file.getName() + "——再审首页");
				break;
			case entrustExecution:
				writeFile(args[1], entrustExecution);
				System.out.println(file.getName() + "——委托执行函");
				break;
			case notificationOfProof:
				writeFile(args[1], notificationOfProof);
				System.out.println(file.getName() + "——申请调取证据材料");
				break;
			case askNote:
				writeFile(args[1], askNote);
				System.out.println(file.getName() + "——讯问笔录、审问笔录、询问笔录");
				break;
			case askNoteFront:
				writeFile(args[1], askNoteFront);
				System.out.println(file.getName() + "——询问笔录首页");
				break;
			case propertyInvestigation:
				writeFile(args[1], propertyInvestigation);
				System.out.println(file.getName() + "——查询、冻结、扣划裁定书、协助执行通知书等财产调查和控制手续及回执");
				break;
			case withdrawExecution:
				writeFile(args[1], withdrawExecution);
				System.out.println(file.getName() + "——撤回执行申请书");
				break;
			case executionNote:
				writeFile(args[1], executionNote);
				System.out.println(file.getName() + "——向申请人了解执行线索笔录和向被执行人执行笔录");
				break;
			case executionNoteFront:
				writeFile(args[1], executionNoteFront);
				System.out.println(file.getName() + "——首页-向申请人了解执行线索笔录和向被执行人执行笔录");
				break;
			case stopExecution:
				writeFile(args[1], stopExecution);
				System.out.println(file.getName() + "——执行期限延长、中止手续");
				break;
			case executionBasis:
				writeFile(args[1], executionBasis);
				System.out.println(file.getName() + "——执行依据和生效证明");
				break;
			case executionBasisFront:
				writeFile(args[1], executionBasisFront);
				System.out.println(file.getName() + "——首页-执行依据和生效证明");
				break;
			case socialSurvey:
				writeFile(args[1], socialSurvey);
				System.out.println(file.getName() + "——缓刑适用社会调查表");
				break;
			case socialSurveyFront:
				writeFile(args[1], socialSurveyFront);
				System.out.println(file.getName() + "——首页-缓刑适用社会调查表");
				break;
			case executionNotification:
				writeFile(args[1], executionNotification);
				System.out.println(file.getName() + "——执行通知书存根和回执（释放证回执）");
				break;
			case executionNotificationFront:
				writeFile(args[1], executionNotificationFront);
				System.out.println(file.getName() + "——首页-执行通知书存根和回执（释放证回执）");
				break;
			case courtNotification:
				writeFile(args[1], courtNotification);
				System.out.println(file.getName() + "——公诉人、辩护人出庭通知书");
				break;
			case postponeTrial:
				writeFile(args[1], postponeTrial);
				System.out.println(file.getName() + "——延长审限的决定、报告及批复");
				break;
			case preCourtConferenceNote:
				writeFile(args[1], preCourtConferenceNote);
				System.out.println(file.getName() + "——庭前会议笔录");
				break;
			case preCourtConferenceNoteFront:
				writeFile(args[1], preCourtConferenceNoteFront);
				System.out.println(file.getName() + "——首页——庭前会议笔录");
				break;
			case preCourtWorkNote:
				writeFile(args[1], preCourtWorkNote);
				System.out.println(file.getName() + "——庭前工作笔录");
				break;
			case preCourtWorkNoteFront:
				writeFile(args[1], preCourtWorkNoteFront);
				System.out.println(file.getName() + "——首页——庭前工作笔录");
				break;
			case defense:
				writeFile(args[1], defense);
				System.out.println(file.getName() + "——辩护词");
				break;
			case defenseFront:
				writeFile(args[1], defenseFront);
				System.out.println(file.getName() + "——首页——辩护词");
				break;
			case defendantStatement:
				writeFile(args[1], defendantStatement);
				System.out.println(file.getName() + "——被告陈述词");
				break;
			case defendantStatementFront:
				writeFile(args[1], defendantStatementFront);
				System.out.println(file.getName() + "——首页——被告陈述词");
				break;
			case mediation:
				writeFile(args[1], mediation);
				System.out.println(file.getName() + "——刑事附带民事部分调解书");
				break;
			case postponeTrialForm:
				writeFile(args[1], postponeTrialForm);
				System.out.println(file.getName() + "——延长审限的批复");
				break;
			case courtSummon:
				writeFile(args[1], courtSummon);
				System.out.println(file.getName() + "——提神、询问当事人、提押票、传票");
				break;
			case cost:
				writeFile(args[1], cost);
				System.out.println(file.getName() + "——缴纳诉讼费");
				break;
			case proofOfService:
				writeFile(args[1], proofOfService);
				System.out.println(file.getName() + "——送达回证");
				break;
			default:
				if (Identification.check(img)) {
					writeFile(args[1], identification);
					System.out.println(file.getName() + "——授权委托书、身份证明");
				}else {
					writeFile(args[1], evidence);
					System.out.println(file.getName() + "——证据");
					break;
				}
			}
		}
	}
	public static void writeFile(String path, String content){
		File file = new File(path);
		try {
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(content);
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static BufferedImage removeStamp(BufferedImage img){
		int size = img.getHeight() / 12;
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				img.setRGB(x, y, Color.white.getRGB());
			}
		}
		return img;
	}
	
}
