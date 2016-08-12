package process;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Document implements FileCode {
	public static String contentEndCheck(BufferedImage img, int[] row) {
		int lineNum = 0;
		ArrayList<Integer> seperator = new ArrayList<>();

		int h = img.getHeight();
		int w = img.getWidth();

		int lineH = h / 170;
		int up = 0;
		int down = 0;
		for (int y2 = h - 1; y2 > 0; y2--) {
			up = 0;
			down = 0;
			for (int i = y2; i > 0; i--) {
				if (row[i] > 10) {
					y2 = i - 1;
					down = i + 5 < h - 1 ? i + 5 : h - 1;
					break;
				}
				if (i == 0) {
					y2 = 0;
				}
			}
			for (int i = y2; i > 0; i--) {
				if (row[i] < 13) {
					y2 = i;
					up = i - 5 > 0 ? i - 5 : 0;
					break;
				}
				if (i == 0) {
					y2 = 0;
				}
			}
			if ((down - up > lineH) && (down - up < h * 0.5D)) {
				seperator.add(Integer.valueOf(up));
				seperator.add(Integer.valueOf(down));
				lineNum++;
				BufferedImage lineImg = img.getSubimage(0, up, w, down - up);
				File imgFile2 = new File(System.currentTimeMillis() + ".png");
				try {
					ImageIO.write(lineImg, "png", imgFile2);
				} catch (IOException e) {
					e.printStackTrace();
				}
				String txt = OCR.recognizeText(imgFile2, "-psm 7");
				if (txt.isEmpty()) {
					txt = OCR.recognizeText(imgFile2, "");
				}
				System.out.println("12_" + txt);
				imgFile2.delete();

				if (txt.matches(".*为其诉讼代理.{1,3}|.*代理诉讼.{0,3}|.{0,4}授权委[托抚拄]书.{0,5}|.*委托.{0,4}" + 
				"|.{0,5}法律服务.{0,5}")) {
					return identification;
				}
				if (txt.matches(".*撤回.{0,3}执行.{0,4}")) {
					return "30";
				}
			}
			if (lineNum > 5) {
				return "";
			}
		}
		return "";
	}

	public static boolean fileEndCheck(BufferedImage img, int[] row, double threshold) {
		int h = img.getHeight();
		int blankLen = 0;
		for (int y = h - 1; y > 0; y--) {
			if ((row[y] > 20) && (row[(y - 1)] > 20)) {
				break;
			}
			blankLen++;
		}

		if (blankLen > h * threshold) {
			for (int y = 0; y < h; y++) {
				int up = 0;
				int down = 0;
				for (int i = y; i < h; i++) {
					if (row[i] > 15) {
						up = i;
						y = i;
						break;
					}
					if (i == h - 1) {
						y = h;
					}
				}
				for (int i = y; i < h; i++) {
					if (row[i] < 15) {
						down = i;

						if (down - up > h * 0.1D) {
							return false;
						}
						y = i;
						break;
					}
				}
			}
			System.out.println("file blank end");
			return true;
		}
		return false;
	}

	public static boolean endCheck(BufferedImage img, int[] row) {
		int lineNum = 0;
		ArrayList<Integer> seperator = new ArrayList<>();

		int h = img.getHeight();
		int w = img.getWidth();

		int lineH = h / 100;
		int up = 0;
		int down = 0;
		for (int y2 = h - 1; y2 > 0; y2--) {
			up = 0;
			down = 0;
			for (int i = y2; i > 0; i--) {
				if (row[i] > 10) {
					y2 = i - 1;
					down = i + 5 < h - 1 ? i + 5 : h - 1;
					break;
				}
				if (i == 0) {
					y2 = 0;
				}
			}
			for (int i = y2; i > 0; i--) {
				if (row[i] < 13) {
					y2 = i;
					up = i - 5 > 0 ? i - 5 : 0;
					break;
				}
				if (i == 0) {
					y2 = 0;
				}
			}
			if ((down - up > lineH) && (down - up < h * 0.5D)) {
				seperator.add(Integer.valueOf(up));
				seperator.add(Integer.valueOf(down));
				lineNum++;
				BufferedImage lineImg = img.getSubimage(0, up, w, down - up);
				File imgFile2 = new File(System.currentTimeMillis() + ".png");
				try {
					ImageIO.write(lineImg, "png", imgFile2);
				} catch (IOException e) {
					e.printStackTrace();
				}
				String txt = OCR.recognizeText(imgFile2, "-psm 7");
				if (txt.isEmpty()) {
					txt = OCR.recognizeText(imgFile2, "");
				}
				System.out.println("12_" + txt);
				imgFile2.delete();

				if (txt.matches(".{0,2}[此北]致.{0,5}|.{0,2}具状.*|.{0,2}原告人.*|.{0,2}申请.{2,6}|.{0,2}答[辨辩].{2,6}"
						+ "|.{1,8}[耳年].*[月目用门].*[口日曰白门].{0,3}|.{3,10}[人八]民法[阮院].{0,2}|.{0,1}附.{0,1}"
						+ "|.*起诉人:.*|.{1,6}人:.{2,4}|[此北].|.{0,2}[上公]诉[人八]:.{1,4}|[申巾]请[人八]：.{0,3}"
						+ "|法定代表.{0,3}[(]印[章童][)]|.*法律服务所|.*律[师帅]事务所|.{1,10}律师|.?[窜审]判[长员].{1,5}"
						+ "|.{2,8}局|负责人:.*|[辨辩]护[人儿].{2,4}|年月[日曰门]|谢谢|检察员:.*|.{2,6}[市巾]级.{0,3}法院.{0,4}")) {
					System.out.println("file end feature");
					return true;
				}
			}
			if (lineNum > 4) {
				return false;
			}
		}
		return false;
	}

	public static String check(BufferedImage img) {
		int h = img.getHeight();
		int w = img.getWidth();
		int lineH = h / 85;
		img = img.getSubimage((int) (w * 0.1D), (int) (h * 0.05D), (int) (w * 0.8D), (int) (h * 0.86D));
		// byte[][] pixels = Utils.OSTUbinarition(img);
		byte[][] pixels = Utils.binarition(img, 750);
		img = Utils.rectify(pixels);
		pixels = Utils.binarition(img, 750);
		pixels = Utils.repair(pixels);
		img = Utils.pixels2Image(pixels);

		h = img.getHeight();
		w = img.getWidth();
		ArrayList<Integer> seperator = new ArrayList<>();
		int[] row = new int[h];
		row = Utils.Tohisto(img, "row");
		BufferedImage lineImg = null;
		String txt = null;
		int lineNum = 0;
		for (int y = 0; y < h; y++) {
			int up = 0;
			int down = 0;
			for (int i = y; i < h; i++) {
				if (row[i] > 10) {
					y = i + 1;
					up = i - 5 > 0 ? i - 5 : 0;
					break;
				}
				if (i == h - 1) {
					y = h;
				}
			}
			for (int i = y; i < h; i++) {
				if (row[i] < 13) {
					y = i;
					down = i + 5 > h - 1 ? h - 1 : i + 5;
					break;
				}
				if (i == h - 1) {
					y = h;
				}
			}
			if ((down - up > lineH) && (down - up < h * 0.35D)) {
				seperator.add(Integer.valueOf(up));
				seperator.add(Integer.valueOf(down));
				lineImg = img.getSubimage(0, up, w, down - up);
				if (lineImg == null) {
					continue;
				}
				lineNum++;
				File imgFile = new File(System.currentTimeMillis() + ".png");
				try {
					ImageIO.write(lineImg, "png", imgFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				txt = OCR.recognizeText(imgFile, "-psm 7");
				if ((txt.isEmpty()) || (txt.matches("\\w"))) {
					txt = OCR.recognizeText(imgFile, "");
				}
				System.out.println("1_" + txt);
				imgFile.delete();
				if (txt.matches(".*[仲伸]裁委[员贝].{1,3}")) {
					int num = 0;
					for (int y2 = down + 1; y2 < h; y2++) {
						for (int i = y2; i < h; i++) {
							if (row[i] > 10) {
								y2 = i + 1;
								up = i - 5 > 0 ? i - 5 : 0;
								break;
							}
						}
						for (int i = y2; i < h; i++) {
							if (row[i] < 13) {
								down = i + 5 > h - 1 ? h - 1 : i + 5;
								y2 = i + 1;
								break;
							}
						}
						if ((down - up > lineH) && (down - up < h * 0.35D)) {
							lineImg = img.getSubimage(0, up, w, down - up);
							if (lineImg != null) {
								imgFile = new File(System.currentTimeMillis() + ".png");
								num++;
								try {
									ImageIO.write(lineImg, "png", imgFile);
								} catch (IOException e) {
									e.printStackTrace();
								}
								txt = OCR.recognizeText(imgFile, "-psm 7");
								if ((txt.isEmpty()) || (txt.matches("\\w"))) {
									txt = OCR.recognizeText(imgFile, "");
								}
								System.out.println(txt);
								if (txt.matches(".{0,2}[裁截]决书")) {
									if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
										return "31";
									}
									return "31a";
								}
								if (num > 2)
									break;
							}
						}
					}
					return "31";
				}
				if (txt.matches(".*律师事务所公函|.{2,5}援助公函")) {
					return "5";
				}
				if (txt.matches(".*公函")) {
					String result = contentEndCheck(img, row);
					if (result == "5")
						return "5";
				} else {
					if (txt.matches("申请书")) {
						if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
							String result = contentEndCheck(img, row);
							if (result == "30")
								return "30";
							if (result == "5") {
								return "5";
							}
							return execution;
						}
						return executionFront;
					}
					if (txt.matches(".{0,2}撤销.{2,6}|.{0,2}[攒撤撒]诉.*|.{0,3}[拆诉]申请.{0,3}")) {
						if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
							return supersedeas;
						}
						return supersedeasFront;
					}
					if (txt.matches(".{0,2}答.?[辨辩]状.{0,6}|.{0,5}答.?[辨辩]意见.{0,6}|行[政正].?答.{2,4}")) {
						if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
							return answer;
						}
						return answerFront;
					}
					if (txt.matches(".{2,4}司法局回复函|.*社会调[杳查]表")) {
						return socialSurvey;
					}
					if (txt.matches(".{0,3}评估意.{0,3}|.{2,4}调[杳查]报告|.{3,7}风.{1,3}估报告|.{0,3}评.{1,3}见.{0,2}")) {
						if ((fileEndCheck(img, row, 0.2D)) || (endCheck(img, row))) {
							return socialSurvey;
						}
						return socialSurveyFront;
					}

					if (txt.matches(".*调.{0,3}笔.{0,3}录.{0,3}")) {
						return compromise;
					} else if (txt.matches(".*授.{0,2}[权杈].{0,3}[托抚].?书.{0,4}"
							+ "|.{0,5}委.{1,3}"
							+ "|委[托抚拄]书编号.{2,7}.{0,5}身[纷份]证[明朋].{0,3}|.{0,2}公.{1,5}.{1,4}[\\d]{7,}.*"
							+ "|.*律.{2,5}所[^信]?函.{0,6}|法人身[纷份]证.*|.*代码信息.*|.*执业证类别.*"
							+ "|.*有限公司注册.*一社会信用.*[\\d]{6,}|.{0,3}[法定代表人身[纷份]证复印件]{5,}.{0,4}"
							+ "|.?[法去]定.{0,2}[戈弋代伐]表人身.{0,2}[分纷份].{0,2}[明阴]书"
							+ "|推荐信|.*援助中心通知函|授权书|.*出庭函|.{0,2}业[机矶][构枸恂].*"
							+ "|执业证.*|.{0,4}居民户口簿.*|.?推荐信.?|[授投]权书|.{0,8}法人证书|.*度.{0,3}核备.*"
							+ "|.{0,3}注册号[\\d]{7,18}.{0,3}|.{0,3}登记项目|.{2,6}证[明朋]|.?组织.构代码证.*|中华人民共和.?国.?"
							+ "|律.{2,5}所[^信]?函.{0,5}第.{1,6}号|.{2,8}律师事务所|营.[执捌]照|.*统一社会信用代.*"
							+ "|亍聿师事.{1,4}所函|.{0,6}律师事.{1,3}函|.{0,6}律师事务.{0,4}|授[权衩杈]委.{2,6}"
							+ "|.{0,4}身[份伯]证明.?|.*一[社祉]会信用代.*"
							+ "|.{1,3}身.?号码[\\d]{7,}|.明|法人(代表)?.?[证正]明.{0,6}|.?授.{1,3}" + "|律师.?务所名称.{2,9}"
							+ "|法律工作者证|组织机构代码证|统一社会信用代码|推荐信|法律服务所.{0,3}")) {
						return identification;
					}

					if (txt.matches("[悔梅]过书")) {
						if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
							return penitence;
						}
						return penitenceFront;
					}
					if (txt.matches(".*公[诉拆].?见书")) {
						if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
							return stateOfPubProsecution;
						}
						return stateOfPubProsecutionFront;
					}
					// 辩护词得在代理书前面
					if (txt.matches(".{0,8}[辨辩]护.{1,7}|[辨辩].[词询]|.*辩护词")) {
						if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
							return defense;
						}
						return defenseFront;
					}
					if (txt.matches(".{0,6}[代筏].?词.?|.*代理意见.{0,3}|.*代[王壬][里理]词|.*代[王壬]里意见.{0,3}")) {
						if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
							return "7";
						}
						return "7a";
					}
					if (txt.matches(".{0,7}[冉再][审窜].{0,4}|.{0,8}申诉.{1,3}")) {
						if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
							return "18";
						}
						return "18a";
					}
					if (txt.matches(".{0,5}诉讼代理人推荐函.{0,4}"))
						return agentRecommendation;
					if (!txt.matches(".*移送.*")) {
						if (txt.matches(".{2,5}上诉.{0,2}|.{0,6}抗诉.{0,6}|.{0,3}上诉状.{0,5}|行.?上.?状|行政.?诉状.{0,6}"
								+ "|.{2,6}上诉.{0,1}状")) {
							if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
								return protest;
							}
							return protestFront;
						}
					}

					if (txt.matches("[^送达]{0,8}[诉讲拆][状书].{0,5}|.*加.{0,2}讼.{0,10}|.*诉讼申.*|.{0,6}再.申请[书]{0,1}自诉状"
							+ "|.?自述材料.?|.{0,3}附带民事诉|送达.{0,2}起[诉讲讶拆].{0,3}|[起赳].书|"
							+"变更诉讼请求申请书|变更起诉决定书|诉讼请求申请")) {
						if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
							return indictment;
						}
						return indictFront;
					}
					if (txt.matches(".{0,7}和解协议[书]?|.{0,3}谅.?书")) {
						return mediation;
					}
					if (txt.matches(".{0,3}[和调]解.{2,3}|调.{0,2}申请书|.{2,6}议书|.{0,2}[谅凉][解牌].{0,2}|民事赔偿协议书")) {
						if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
							return compromise;
						}
						return compromiseFront;
					}

					if (txt.matches("执行通知书.{0,5}|.{3,8}发还裁决款审批表|.{0,5}收.?条")) {
						return "33";
					}
					if (txt.matches(".*保全申请书|.*账户信息|.{0,3}矫正告知书|.{0,4}矫正人员.{2,5}通知单")) {
						if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
							return "33";
						}
						return "33a";
					}
					if (txt.matches(".{0,8}判[决泱]书.?|.{0,3}裁.{0,3}定.{0,3}|裁定书原稿|.{0,8}民事调.{0,4}书.?|.{0,8}民事裁定.{0,2}书.*"
							+ "|.{0,8}民.?判[决泱].{0,2}书.?|民.?判[决泱].?")) {
						if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
							return judgement;
						}
						return judgementFront;
					}
					if (txt.matches(".*执行工作日志"))
						return "20";
					if (txt.matches(
							".{0,3}复议申请书.*|.{0,6}异议申请书|执行异议书.{0,4}|.*行.{0,3}申请书.{0,2}|申请执行书.{0,6}" + "|.{0,2}强制执行.*")) {
						if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
							return execution;
						}
						return executionFront;
					}
					if (txt.matches(".{0,1}附.{0,1}"))
						return end;
					if (txt.matches("^.{0,2}换[押砷].*|.{0,4}[提捉]讯.{0,5}|换.证.?"
							+ "^拘传票|换[押砷]票|[提捉][押砷]票"))
						return "21";
					if (txt.matches(".?证人出庭作证申请书.{0,4}"))
						return "23";
					if (txt.matches(".?委托宣判函.{0,4}"))
						return "24";
					if (txt.matches(".问.录")) {
						if ((fileEndCheck(img, row, 0.06D)) || (endCheck(img, row))) {
							return "14";
						}
						return "14a";
					}
					if (txt.matches(".?委托执行函.{0,4}"))
						return "25";
					if (txt.matches(".{0,5}证据光盘目录.*|调.{0,3}证据申请书")) {
						return "26";
					}
					if (txt.matches("证.?[据居].{0,4}|案件目录|.?受案登记表.?|.?受案回执.?|鉴定意见书|质证.{0,2}录"
							+ "|.*气象证[明朋].*|.*被罚.*|.*罚款处.*|.{0,4}鉴定书.{0,3}|.*损车牌.*|房屋登记.{0,2}"
							+ "|征收.{0,4}清单.?|延期举证申请书|婚姻登记记录.{0,4}|一、居.?户口.?具.*|.?常住.{0,4}记卡.{0,4}|注意事项"
							+ "|.{2,8}列表|.{0,3}调[杳查]笔录.{0,7}|情况说[明阴]|.{0,1}情况说日月.{0,2}")) {
						return evidence;
					}
					if (txt.matches("领款审批表.{0,4}"))
						return propertyInvestigation;
					if (txt.matches(".{0,2}案申[清请]"))
						return "28";
					if (txt.matches("执行笔录")) {
						if ((fileEndCheck(img, row, 0.2D)) || (endCheck(img, row))) {
							return "29";
						}
						return "29a";
					}
					if (txt.matches(".{0,5}出.?法庭通知书.{0,2}|申请证人出庭作证申请书"))
						return "34";
					if (txt.matches(".{0,3}延期审理.{0,5}"))
						return "35";
					if (txt.matches(".*讼费收.{0,2}|.*诉讼费专用票据.*")) {
						return cost;
					}
					if (txt.matches(
							"邮件号码.*|.*投递并签收.*|.*邮.{1,2}号码:.*|运单.程|.*揽投员.*|.*[送达]达.{0,3}"
							+ "|.*EMS.*" + "|.{0,6}网上寄件.?|.*送达.*送达.*")) {
						return proofOfService;
					}
					if (txt.matches("保证书|.*担保书|法庭笔录|庭审笔录.?|法庭审[理埋]笔录|.*审判笔录|.?开庭笔录.?"
							+"法庭审王里笔录|.?是否公开.?公开审理|.?是否公开.?不公开审理")) {
						if ((fileEndCheck(img, row, 0.2D)) || (endCheck(img, row))) {
							return guarantee;
						}
						return guaranteeFront;
					}
					if (txt.matches("庭前会议笔录|证据交换笔录")) {
						if ((fileEndCheck(img, row, 0.2D)) || (endCheck(img, row))) {
							return preCourtConferenceNote;
						}
						return preCourtConferenceNoteFront;
					}
					if (txt.matches("庭前工作笔录")) {
						if ((fileEndCheck(img, row, 0.2D)) || (endCheck(img, row))) {
							return preCourtWorkNote;
						}
						return preCourtWorkNoteFront;
					}
					if (txt.matches(".?传[票禀].{0,8}")) {
						return courtSummon;
					}
					// -----------------------------------------------------------------
					if (txt.matches(".*证据.*交换.*笔录.*")) {
						return exchangeOfNotes;
					}

					if (txt.matches(".{1,7}案件.{1,7}流.*管理.{1,3}|.*立案登记表.{1,6}|立案审批表|.*案件.*审判流.*"
							+ "|.*立案.*理.*息|.*案件.*立案.*审.*")) {

						return CaseFlow;
					}

					if (txt.matches("改变管辖通知书.{1,7}|.*指定管辖决定书.*")) {

						return NoticeOfChangeInJurisdiction;
					}

					// if (txt.matches(".{0,8}立案.?知书.{0,5}|.*补充材料通知书.{0,5}"
					// + "|.{0,8}应[诉拆].?知书.{0,5}")) {
					//
					// return FilingNoticeOfAcceptance;
					// }
					if (txt.matches(".{0,8}[立案|应诉|应拆].?知书.{0,5}|.*补充材料通知书.{0,5}")) {

						return FilingNoticeOfAcceptance;
					}

					if (txt.matches(".*阅卷.*知书.*")) {
						return MarkingNotice;
					}

					if (txt.matches(".*简易程序.*")) {
						if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
							return SimpleProceduresApply;
						}
						return SimpleProceduresApplyFront;
					}

					if (txt.matches(".*送达起诉书.{0,2}笔录")) {
						return ServiceOfTheIndictment;
					}
					

					if (txt.matches(".{0,2}司法公.{0,2}知书.?")) {
						return PublicationOfThisBookJusticeFront;
					}
					if (txt.matches(".{0,3}监督.{0,2}|.*诉讼[冈风凤]险.{0,5}")) {
						if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
							return PublicationOfThisBookJustice;
						}
						return PublicationOfThisBookJusticeFront;
					}

					if (txt.matches(".*保证书.{5,11}|.{1,3}保.{0,3}候审.{1,3}通知书.{1,6}"
							+ "|.{0,2}居住.{0,2}通知书.{1,6}|.{1,3}保.{0,3}候审.{1,3}决定书.{1,6}" + "|执行.{0,2}通知书.{0,5}")) {
						return CompulsoryMeasuresChangeDecision;
					}

					if (txt.matches("查封.{3,7}财产.{1,7}|查询存款函.{1,6}|诉讼保全.{0,3}")) {
						return LitigationHolds;
					}

					if (txt.matches("准许调[取查].{0,3}[书令].{0,4}")) {
						return PermitTheTransferOfEvidence;
					}

					if (txt.matches(".*鉴定结论|.{0,4}鉴定委托书.{4,10}|.{0,2}笔录.{7}")) {
						return ExpertConclusions;
					}

					if (txt.matches("被告人坦白.{0,5}问题登记表|查证材料")) {
						return RegistrationFormAndCheckMaterial;
					}

					if (txt.matches("限制出境决定书.{7}")) {
						return RestrictExitDecision;
					}

					if (txt.matches(".{0,2}申请回避.{1,3}决定书.{7}")) {
						return WithdrawalByPetition;
					}

					if (txt.matches("[出开]庭通知书.{7}|.{1,4}员出.?法庭通知书|.*告知书|.*合议庭.*成.*员.*通知书.*")) {
						return NoticeOfTheHearing;
					}

					if (txt.matches(".*公[告古].{0,8}")) {
						return CourtPapersAnnouncement;
					}

					if (txt.matches(".*刑建议书.*")) {
						return SentencingRecommendation;
					}

					if (txt.matches(".{2}决定书.{7}")) {
						return PrejudiceCriminalProceedingsDetention;
					}

					if (txt.matches("刑事裁定书.*准许.{0,5}|.*刑[辜事]判决书.*|.*刑事附带民事.{1,3}")) {
						if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
							return OriginalJudgmentDocument;
						}
						return OriginalJudgmentDocumentFront;
					}

					if (txt.matches(".{0,2}宣判笔录.{0,3}|.判笔.{2,6}|判后释法笔录")) {
						if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
							return SentencingNotes;
						}
						return SentencingNotesFront;
					}

					if (txt.matches(".*司法建议书.{2,7}")) {
						if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
							return JudicialRecommendations;
						}
						return JudicialRecommendationsFront;
					}

					if (txt.matches("报送上.*抗.{0,4}件.{2,6}|.{0,2}上诉案件移送函.{0,2}|.{0,2}案件上诉移送函.{0,2}"
							+ "|.?案件移送函.?|.?报送上.?抗.?.?案件.?|.?报送上诉案件函{0,1}.?")) {
						return ReferTheCaseToTheProtestLetter;
					}

					if (txt.matches(".*退.*卷.*函.*|.?卷函")) {
						return UnwindingLetter;
					}

					if (txt.matches(".{1,3}执行死刑命令.{0,4}死刑.{1,3}")) {
						return ExecutionOrder;
					}

					if (txt.matches(".*暂停执行死刑的报告及批复.*")) {
						return AMoratoriumOnExecutions;
					}

					if (txt.matches(".*验明正身笔录.{0,2}死刑用.{0,2}")) {
						return NotesPositivelyIdentified;
					}

					if (txt.matches(".*执行死刑笔录.{0,2}刑事.{0,3}")) {
						return NotesExecutions;
					}

					if (txt.matches(".*执行死刑报告")) {
						return ExecutionReport;
					}

					if (txt.matches("死刑罪犯照片")) {
						return ExecutionsBeforeAndAfterPhotos;
					}

					if (txt.matches(".*领取骨灰通知书.{3,9}")) {
						return CondemnedFamiliesReceiveAshes;
					}

					if (txt.matches(".*尸体处理登记表.*")) {
						return CarcassDisposalRegistrationForm;
					}

					if (txt.matches("执行通知书.{3,10}用.?|减刑执行通知书.*用.?" + "|释放通知书.{3,10}用.?|假释执行通知书.*用.?")) {
						return EnforcementNotice;
					}

					if (txt.matches("发还财物品清单.{1,6}")) {
						return evidenceHandlingProceduresAndMaterialTransferList;
					}

					if (txt.matches(".*减刑.*假.*定书")) {
						if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
							return CommutationParoleRuling;
						}
						return CommutationParoleRulingFront;
					}

					if (txt.matches(".*备考表.*")) {
						return RemarksTable;
					}

					if (txt.matches(".*卷内目录.*")) {
						return Juanneimulu;
					}

					if (txt.matches(".*送达.*地址.*书.{0,6}|.*当事.*确认书.{0,6}")) {
							return EvidenceAnoticeInTheAddressConfirmation;
						}
					
					if (txt.matches(".*举证通知书.*|.*电子送达确认书.*")) {
						if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
							return EvidenceAnoticeInTheAddressConfirmation;
						}
						return EvidenceAnoticeInTheAddressConfirmationFront;
					}

					if (txt.matches("诉讼保全.{0,2}书.{0,2}|.*鉴定委托书.*|.*鉴定结论.*|.*通知书.{2,5}重新.{1,3}申请.{0,2}")) {
						if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
							return LitigationPreservationGuarantee;
						}
						return LitigationPreservationGuaranteeFront;
					}

					if (txt.matches(".*通知书.{2,5}延长.{2,4}申请.{0,2}|.*通知书.*当事人.*第三人.*|.*通知书.{3,9}举证期限.{0,3}")) {
						return EvidenceChangeNoticePeriod;
					}

					if (txt.matches("申请.*程序.{2,4}|转换程序通知书|民事.{1,6}程序.{1,3}程序.{1,2}")) {
						return ChangeTheOrdinaryProcedureForApproval;
					}

					if (txt.matches(".*证物处理手续.{1,3}")) {
						return EvidenceHandlingProcedures;
					}

					if (txt.matches(".*受.?[里理]通知书|驳回申诉")) {
						return CaseAcceptanceNotice;
					}

					if (txt.matches(".*[不上准].*裁定书")) {
						if ((fileEndCheck(img, row, 0.25D)) || (endCheck(img, row))) {
							return ImplementationOfTheDecision;
						}
						return ImplementationOfTheDecisionFront;
					}

					if (txt.matches(".*财产.*线索.*报告")) {
						return PropertyCluesAndReports;
					}

					if (txt.matches(".*执行.*进.*知书")) {
						return TheImplementationProcessOfThisBook;
					}

					if (txt.matches("由法院.*有关财产.{4,8}|.{1,3}拍卖措施.{1,3}|.*成交确认.{0,4}"
							+ "|.*变卖措施.{1,4}|.*以物抵债.{1,5}|鉴定委托书|价格评估委托书|拍卖.{0,3}卖.*委托书"
							+ "|拍卖通知书|查封公告|查封.{4,8}财产清单|拍卖公告|.*迁出房屋.{0,2}退出土地.{0,3}"
							+ "|搜查令|.{0,3}交出财物.{0,6}|.{1,5}生效法律.{1,4}行为.{0,6}" + "|折价赔偿.*财产.{1,5}|代为完成.*"
							+ "|.{1,3}追回财物.{1,6}")) {
						return auctionProceduresForRealizationOfProperty;
					}

					if (txt.matches(".{0,7}执行争议.*|.*利害关系.*|案外人.*|复议执行.{1,4}"
							+ "|督促执行令|.*下级法院.*|.*暂缓执行.*|.*非诉法律文书.*|.*执行裁定.*")) {
						return ProcessingExecutionDisputeBooks;
					}

					if (txt.matches("执行款过户手续.{2}|领款审批表")) {
						return ExecutiveShallTransferProcedures;
					}

					if (txt.matches(".*执行回转.*")) {
						return Swivel;
					}

					if (txt.matches(".*结案.{0,2}通知.{0,2}书")) {
						return NotificationClosed;
					}
				}
			}
			if (lineNum > 3) {
				break;
			}

		}

		lineNum = 0;
		seperator.clear();
		for (int y = h - 1; y > 0; y--) {
			int up = 0;
			int down = 0;
			for (int i = y; i > 0; i--) {
				if (row[i] > 10) {
					y = i - 1;
					down = i + 5 < h - 1 ? i + 5 : h - 1;
					break;
				}
				if (i == 0) {
					y = 0;
				}
			}
			for (int i = y; i > 0; i--) {
				if (row[i] < 10) {
					y = i;
					up = i - 5 > 0 ? i - 5 : 0;
					break;
				}
				if (i == 1) {
					y = 0;
				}
			}
			if ((down - up > lineH) && (down - up < h * 0.35D)) {
				seperator.add(Integer.valueOf(up));
				seperator.add(Integer.valueOf(down));

				lineImg = img.getSubimage(0, up, w, down - up);
				lineNum++;
				File imgFile = new File(System.currentTimeMillis() + ".png");
				try {
					ImageIO.write(lineImg, "png", imgFile);
				} catch (IOException e) {
					e.printStackTrace();
				}
				txt = OCR.recognizeText(imgFile, "-psm 7");

				if (txt.isEmpty()) {
					txt = OCR.recognizeText(imgFile, "");
				}
				System.out.println("2-" + txt);
				imgFile.delete();
				if (txt.matches("账号.{0,2}[\\d]{5,16}|开户行.*|.{1,5}省.{0,3}收[入人].{2,6}")) {
					return cost;
				}

				if (txt.matches(".{0,2}[此北]致.{0,5}|具状.{2,6}|答[辨辩].*|谢谢法庭.*"
						+ "|.{1,9}[耳年].{1,3}[月目用门].{1,4}[口日曰白门].{0,4}|.{3,10}法[阮院脘][^起诉]{0,4}"
						+ "|.{2,6}[级区]人民法.{1,2}|.{0,1}附.{0,1}|.*光盘.张.{0,2}|[\\d].{0,4}卷宗.{2,4}"
						+ "|.*起诉人:.*|[此北].{1,3}|.{0,2}[上公]诉[人八]:.{1,4}|[申巾]请[人八]:.{0,3}|附:.*"
						+ "|.*法律服务所|.*律[师帅]事务所|.{1,10}律师|.?[窜审]判[长员].{1,5}|被代:.*"
						+ "|负责人：.*|.?社区矫正对.{2,6}|[辨辩]护[人儿].{2,4}|年月[日曰门]|谢谢|检察员:."
						+ "|.{3,10}人[民尺].?[院浣脘][^起诉]{0,7}|.{2,5}司.局|.{3,9}挥部"
						+ "|.{2,8}办公室.{1,6}[耳年].{0,3}[月目用门].{0,4}[口日曰白门].{0,4}|.{0,4}市.{2,7}民法.?"
						+ "|.{1,6}[耳年].{0,3}[月目用门].?[\\d].{0,2}|[\\d]{3,5}年.{2,7}" 
						+ "|.{1,3}[市币].{1,3}区人民.{1,5}|当事人核对笔录无误.{1,5}")) {
					return end;
				}

				if (txt.matches("委托人.{0,7}|.{0,5}[人居]民.{2,8}证|.*中华.*公安部.*|律师年.{0,2}度.*案.{0,2}|.?执业证.*"
						+ "|.{0,3}[常住人口登记卡]{4,}.{0,4}|[中华人民共和国]{5,}"
						+ "|成立日期.{1,5}[耳年].{0,3}[月目用门].{0,4}[日曰白].{0,3}|.*律师.度.核备案.*"
						+ "|发证[曰日]期.*|.[效姣].限.[\\d\\w].{0,4}.*|.*度.{0,3}核备.*|中.{0,4}民共和国"
						+ "|签发机关.{0,4}公安局|.{0,2}公民.?份号码[\\d]{7,}|.*一[社祉]会信用代.*")) {
					return identification;
				}
				if (txt.matches(".*投递员签.{0,3}|.*代收人签名.*|.*收寄[邮邯]件.*|.*邮件运.*|.*投递并签.*"))
					return proofOfService;
				if (txt.matches(".{0,5}收.?条")) {
					return executionNotification;
				}
			}
			if (lineNum > 7) {
				break;
			}
		}
		return "";
	}

	public static void main(String[] args) {
		long time1 = System.currentTimeMillis();
		File dir = new File("test");
		BufferedImage img = null;
		for (File file : dir.listFiles()) {
			System.out.println(file.getName());
			try {
				img = ImageIO.read(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
			img = Main.removeStamp(img);
			String result = check(img);
			System.out.println(result);

		}
		long time2 = System.currentTimeMillis();
		System.out.println(time2 - time1);
	}
}