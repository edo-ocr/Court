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
				writeFile(args[1], indictFront);
				System.out.println(file.getName() + "——起诉书首页");
				break;
			case guarantee:
				writeFile(args[1], guarantee);
				System.out.println(file.getName() + "——保证书、担保书");
				break;
			case guaranteeFront:
				writeFile(args[1], guaranteeFront);
				System.out.println(file.getName() + "——保证书、担保书首页");
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
				writeFile(args[1], representativeFront);
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
			case compromiseFront:
				writeFile(args[1], compromiseFront);
				System.out.println(file.getName() + "——和解协议首页");
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
				System.out.println(file.getName() + "——提审、询问当事人、提押票、传票");
				break;
			case cost:
				writeFile(args[1], cost);
				System.out.println(file.getName() + "——缴纳诉讼费");
				break;
			case proofOfService:
				writeFile(args[1], proofOfService);
				System.out.println(file.getName() + "——送达回证");
				break;
//----------------------------------------------------------------
			case exchangeOfNotes:
			    writeFile(args[1], exchangeOfNotes);
			    System.out.println(file.getName() + "——证据交换笔录");
			    break;
			            
			case CaseFlow:
			    writeFile(args[1], CaseFlow);
			    System.out.println(file.getName() + "——案件流程信息表");
			    break;
			            
			case NoticeOfChangeInJurisdiction:
			    writeFile(args[1], NoticeOfChangeInJurisdiction);
			    System.out.println(file.getName() + "——改变管辖通知书");
			    break;
			            
			case FilingNoticeOfAcceptance:
			    writeFile(args[1], FilingNoticeOfAcceptance);
			    System.out.println(file.getName() + "——立案受理通知书");
			    break;
			            
			case MarkingNotice:
			    writeFile(args[1], MarkingNotice);
			    System.out.println(file.getName() + "——阅卷通知书");
			    break;
			            
			case SimpleProceduresApply:
			    writeFile(args[1], SimpleProceduresApply);
			    System.out.println(file.getName() + "——简易程序适用");
			    break;
			            
			case SimpleProceduresApplyFront:
				writeFile(args[1], SimpleProceduresApplyFront);
				System.out.println(file.getName() + "——简易程序适用首页");
				break;
				
			case ServiceOfTheIndictment:
			    writeFile(args[1], ServiceOfTheIndictment);
			    System.out.println(file.getName() + "——送达起诉书笔录");
			    break;
			            
			case PublicationOfThisBookJustice:
			    writeFile(args[1], PublicationOfThisBookJustice);
			    System.out.println(file.getName() + "——司法公开告知书");
			    break;
			            
			case PublicationOfThisBookJusticeFront:
				writeFile(args[1], PublicationOfThisBookJusticeFront);
				System.out.println(file.getName() + "——司法公开告知书首页");
				break;
				
			case CompulsoryMeasuresChangeDecision:
			    writeFile(args[1], CompulsoryMeasuresChangeDecision);
			    System.out.println(file.getName() + "——变更强制措施决定及对家属通知书");
			    break;
			            
			case LitigationHolds:
			    writeFile(args[1], LitigationHolds);
			    System.out.println(file.getName() + "——诉讼保全裁定书、搜查、勘验、查封笔录及查封、扣押物品清单");
			    break;
			            
			case PermitTheTransferOfEvidence:
			    writeFile(args[1], PermitTheTransferOfEvidence);
			    System.out.println(file.getName() + "——当事人、律师调取证据申请、准许调取证据令及调取的证据材料");
			    break;
			            
			case ExpertConclusions:
			    writeFile(args[1], ExpertConclusions);
			    System.out.println(file.getName() + "——赃、证物委托鉴定书及鉴定结论");
			    break;
			            
			case RegistrationFormAndCheckMaterial:
			    writeFile(args[1], RegistrationFormAndCheckMaterial);
			    System.out.println(file.getName() + "——被告人坦白交代、揭发问题登记表及查证材料");
			    break;
			            
			case RestrictExitDecision:
			    writeFile(args[1], RestrictExitDecision);
			    System.out.println(file.getName() + "——限制出境决定书");
			    break;
			            
			case WithdrawalByPetition:
			    writeFile(args[1], WithdrawalByPetition);
			    System.out.println(file.getName() + "——申请回避及处理决定");
			    break;
			            
			case NoticeOfTheHearing:
			    writeFile(args[1], NoticeOfTheHearing);
			    System.out.println(file.getName() + "——开庭通知");
			    break;
			            
			case CourtPapersAnnouncement:
			    writeFile(args[1], CourtPapersAnnouncement);
			    System.out.println(file.getName() + "——开庭公告底稿");
			    break;
			            
			case SentencingRecommendation:
			    writeFile(args[1], SentencingRecommendation);
			    System.out.println(file.getName() + "——量刑建议书");
			    break;
			            
			case PrejudiceCriminalProceedingsDetention:
			    writeFile(args[1], PrejudiceCriminalProceedingsDetention);
			    System.out.println(file.getName() + "——妨害刑事诉讼拘留罚款决定");
			    break;
			            
			case OriginalJudgmentDocument:
			    writeFile(args[1], OriginalJudgmentDocument);
			    System.out.println(file.getName() + "——裁判文书正本");
			    break;
			            
			case OriginalJudgmentDocumentFront:
				writeFile(args[1], OriginalJudgmentDocumentFront);
				System.out.println(file.getName() + "——裁判文书正本首页");
				break;
				
			case SentencingNotesFront:
			    writeFile(args[1], SentencingNotesFront);
			    System.out.println(file.getName() + "——宣判笔录、判后释法笔录首页");
			    break;
			            
			case JudicialRecommendations:
			    writeFile(args[1], JudicialRecommendations);
			    System.out.println(file.getName() + "——司法建议书");
			    break;
			            
			case JudicialRecommendationsFront:
				writeFile(args[1], JudicialRecommendationsFront);
				System.out.println(file.getName() + "——司法建议书首页");
				break;
				
			case ReferTheCaseToTheProtestLetter:
			    writeFile(args[1], ReferTheCaseToTheProtestLetter);
			    System.out.println(file.getName() + "——上抗诉案件移送函（稿）");
			    break;
			            
			case UnwindingLetter:
			    writeFile(args[1], UnwindingLetter);
			    System.out.println(file.getName() + "——退卷函");
			    break;
			            
			case ExecutionOrder:
			    writeFile(args[1], ExecutionOrder);
			    System.out.println(file.getName() + "——执行死刑命令");
			    break;
			            
			case AMoratoriumOnExecutions:
			    writeFile(args[1], AMoratoriumOnExecutions);
			    System.out.println(file.getName() + "——暂停执行死刑的报告及批复");
			    break;
			            
			case NotesPositivelyIdentified:
			    writeFile(args[1], NotesPositivelyIdentified);
			    System.out.println(file.getName() + "——死刑执行前验明正身笔录");
			    break;
			            
			case NotesExecutions:
			    writeFile(args[1], NotesExecutions);
			    System.out.println(file.getName() + "——执行死刑笔录");
			    break;
			            
			case ExecutionReport:
			    writeFile(args[1], ExecutionReport);
			    System.out.println(file.getName() + "——执行死刑报告");
			    break;
			            
			case ExecutionsBeforeAndAfterPhotos:
			    writeFile(args[1], ExecutionsBeforeAndAfterPhotos);
			    System.out.println(file.getName() + "——死刑执行前后照片");
			    break;
			            
			case CondemnedFamiliesReceiveAshes:
			    writeFile(args[1], CondemnedFamiliesReceiveAshes);
			    System.out.println(file.getName() + "——死刑犯家属领取骨灰或尸体通知");
			    break;
			            
			case CarcassDisposalRegistrationForm:
			    writeFile(args[1], CarcassDisposalRegistrationForm);
			    System.out.println(file.getName() + "——尸体处理登记表");
			    break;
			            
			case EnforcementNotice:
			    writeFile(args[1], EnforcementNotice);
			    System.out.println(file.getName() + "——执行通知书");
			    break;
			            
			case evidenceHandlingProceduresAndMaterialTransferList:
			    writeFile(args[1], evidenceHandlingProceduresAndMaterialTransferList);
			    System.out.println(file.getName() + "——赃物、证物移送清单及处理手续材料");
			    break;
			            
			case CommutationParoleRuling:
			    writeFile(args[1], CommutationParoleRuling);
			    System.out.println(file.getName() + "——减刑、假释裁定书");
			    break;
			            
			case CommutationParoleRulingFront:
				writeFile(args[1], CommutationParoleRulingFront);
				System.out.println(file.getName() + "——减刑、假释裁定书首页");
				break;
				
			case RemarksTable:
			    writeFile(args[1], RemarksTable);
			    System.out.println(file.getName() + "——备考表");
			    break;
			            
			case Juanneimulu:
			    writeFile(args[1], Juanneimulu);
			    System.out.println(file.getName() + "——卷内目录");
			    break;
			            
			case EvidenceAnoticeInTheAddressConfirmation:
			    writeFile(args[1], EvidenceAnoticeInTheAddressConfirmation);
			    System.out.println(file.getName() + "——举证通知书、送达地址确认书和电子送达确认书");
			    break;
			            
			case EvidenceAnoticeInTheAddressConfirmationFront:
				writeFile(args[1], EvidenceAnoticeInTheAddressConfirmationFront);
				System.out.println(file.getName() + "——举证通知书、送达地址确认书和电子送达确认书首页");
				break;
				
			case LitigationPreservationGuarantee:
			    writeFile(args[1], LitigationPreservationGuarantee);
			    System.out.println(file.getName() + "——诉讼保全担保书、诉讼保全裁定书正本、鉴定委托书、鉴定结论");
			    break;
			            
			case LitigationPreservationGuaranteeFront:
				writeFile(args[1], LitigationPreservationGuaranteeFront);
				System.out.println(file.getName() + "——诉讼保全担保书、诉讼保全裁定书正本、鉴定委托书、鉴定结论首页");
				break;
				
			case EvidenceChangeNoticePeriod:
			    writeFile(args[1], EvidenceChangeNoticePeriod);
			    System.out.println(file.getName() + "——变更举证期限通知书");
			    break;
			            
			case ChangeTheOrdinaryProcedureForApproval:
			    writeFile(args[1], ChangeTheOrdinaryProcedureForApproval);
			    System.out.println(file.getName() + "——变更普通程序审批表");
			    break;
			            
			case EvidenceHandlingProcedures:
			    writeFile(args[1], EvidenceHandlingProcedures);
			    System.out.println(file.getName() + "——证物处理手续");
			    break;
			            
			case CaseAcceptanceNotice:
			    writeFile(args[1], CaseAcceptanceNotice);
			    System.out.println(file.getName() + "——受理案件通知书");
			    break;
			            
			case ImplementationOfTheDecision:
			    writeFile(args[1], ImplementationOfTheDecision);
			    System.out.println(file.getName() + "——执行裁定");
			    break;
			            
			case ImplementationOfTheDecisionFront:
				writeFile(args[1], ImplementationOfTheDecisionFront);
				System.out.println(file.getName() + "——执行裁定首页");
				break;
				
			case PropertyCluesAndReports:
			    writeFile(args[1], PropertyCluesAndReports);
			    System.out.println(file.getName() + "——财产线索和报告");
			    break;
			            
			case TheImplementationProcessOfThisBook:
			    writeFile(args[1], TheImplementationProcessOfThisBook);
			    System.out.println(file.getName() + "——执行进程告知书");
			    break;
			            
			case auctionProceduresForRealizationOfProperty:
			    writeFile(args[1], auctionProceduresForRealizationOfProperty);
			    System.out.println(file.getName() + "——评估、拍卖等财产变现手续");
			    break;
			            
			case ProcessingExecutionDisputeBooks:
			    writeFile(args[1], ProcessingExecutionDisputeBooks);
			    System.out.println(file.getName() + "——处理执行争议书");
			    break;
			            
			case ExecutiveShallTransferProcedures:
			    writeFile(args[1], ExecutiveShallTransferProcedures);
			    System.out.println(file.getName() + "——执行款过户手续");
			    break;
			            
			case Swivel:
			    writeFile(args[1], Swivel);
			    System.out.println(file.getName() + "——执行回转");
			    break;
			            
			case NotificationClosed:
			    writeFile(args[1], NotificationClosed);
			    System.out.println(file.getName() + "——结案通知书");
			    break;

//-------------------------------------------------------------------			        
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
