package process;

public abstract interface FileCode
{
  public static final String end = "-1";
  public static final String evidence = "0";
  public static final String indictment = "1";
  public static final String indictFront = "1a";
  public static final String answer = "2";
  public static final String answerFront = "2a";
  public static final String cost = "3";
  public static final String proofOfService = "4";
  public static final String identification = "5";
  public static final String socialSurvey = "6";
  public static final String socialSurveyFront = "6a";
  public static final String representative = "7";
  public static final String representativeFront = "7a";
  public static final String guarantee = "8";
  public static final String guaranteeFront = "8a";
  public static final String execution = "9";
  public static final String executionFront = "9a";
  public static final String compromise = "10";
  public static final String compromiseFront = "10a";
  public static final String penitence = "11";
  public static final String penitenceFront = "11a";
  public static final String stateOfPubProsecution = "12";
  public static final String stateOfPubProsecutionFront = "12a";
  public static final String protest = "13";
  public static final String protestFront = "13a";
  public static final String askNote = "14";
  public static final String askNoteFront = "14a";
  public static final String preCourtConferenceNote = "15";
  public static final String preCourtConferenceNoteFront = "15a";
  public static final String preCourtWorkNote = "16";
  public static final String preCourtWorkNoteFront = "16a";
  public static final String supersedeas = "17";
  public static final String supersedeasFront = "17a";
  public static final String reJudge = "18";
  public static final String reJudgeFront = "18a";
  public static final String judgement = "19";
  public static final String judgementFront = "19a";
  public static final String executionLog="20";
  public static final String shift = "21";
  public static final String agentRecommendation= "22";
  public static final String witnessIncourtApplication = "23";
  public static final String entrustJudge = "24";
  public static final String entrustExecution = "25";
  public static final String notificationOfProof = "26";
  public static final String propertyInvestigation = "27";
  public static final String withdrawExecution = "28";
  public static final String executionNote = "29";
  public static final String executionNoteFront = "29a";
  public static final String stopExecution = "30";
  public static final String executionBasis = "31";
  public static final String executionBasisFront = "31a";
  public static final String executionNotification = "33";
  public static final String executionNotificationFront = "33a";
  public static final String courtNotification = "34";
  public static final String postponeTrial = "35";
  public static final String defense = "36";
  public static final String defenseFront = "36a";
  public static final String defendantStatement = "38";
  public static final String defendantStatementFront = "38a";
  public static final String mediation = "40";
  public static final String postponeTrialForm = "41";
  public static final String courtSummon = "43";
//---------------------------------------------------
  public static final String exchangeOfNotes = "44";
  public static final String CaseFlow = "45";
  public static final String NoticeOfChangeInJurisdiction = "46";
  public static final String FilingNoticeOfAcceptance = "47";
  public static final String MarkingNotice = "48";
  public static final String SimpleProceduresApply = "49";
  public static final String SimpleProceduresApplyFront = "49a";
  public static final String ServiceOfTheIndictment = "50";
  public static final String PublicationOfThisBookJustice = "51";
  public static final String PublicationOfThisBookJusticeFront = "51a";
  public static final String CompulsoryMeasuresChangeDecision = "52";
  public static final String LitigationHolds = "53";
  public static final String PermitTheTransferOfEvidence = "54";
  public static final String ExpertConclusions = "55";
  public static final String RegistrationFormAndCheckMaterial = "56";
  public static final String RestrictExitDecision = "57";
  public static final String WithdrawalByPetition = "58";
  public static final String NoticeOfTheHearing = "59";
  public static final String CourtPapersAnnouncement = "60";
  public static final String SentencingRecommendation = "61";
  public static final String PrejudiceCriminalProceedingsDetention = "62";
  public static final String OriginalJudgmentDocument = "63";
  public static final String OriginalJudgmentDocumentFront = "63a";
  public static final String SentencingNotes = "64";
  public static final String SentencingNotesFront = "64a";
  public static final String JudicialRecommendations = "65";
  public static final String JudicialRecommendationsFront = "65a";
  public static final String ReferTheCaseToTheProtestLetter = "66";
  public static final String UnwindingLetter = "67";
  public static final String ExecutionOrder = "68";
  public static final String AMoratoriumOnExecutions = "69";
  public static final String NotesPositivelyIdentified = "70";
  public static final String NotesExecutions = "71";
  public static final String ExecutionReport = "72";
  public static final String ExecutionsBeforeAndAfterPhotos = "73";
  public static final String CondemnedFamiliesReceiveAshes = "74";
  public static final String CarcassDisposalRegistrationForm = "75";
  public static final String EnforcementNotice = "76";
  public static final String evidenceHandlingProceduresAndMaterialTransferList = "77";
  public static final String CommutationParoleRuling = "78";
  public static final String CommutationParoleRulingFront = "78a";
  public static final String RemarksTable = "79";
  public static final String Juanneimulu = "80";
  public static final String EvidenceAnoticeInTheAddressConfirmation = "81";
  public static final String EvidenceAnoticeInTheAddressConfirmationFront = "81a";
  public static final String LitigationPreservationGuarantee = "82";
  public static final String LitigationPreservationGuaranteeFront = "82a";
  public static final String EvidenceChangeNoticePeriod = "83";
  public static final String ChangeTheOrdinaryProcedureForApproval = "84";
  public static final String EvidenceHandlingProcedures = "85";
  public static final String CaseAcceptanceNotice = "86";
  public static final String ImplementationOfTheDecision = "87";
  public static final String ImplementationOfTheDecisionFront = "87a";
  public static final String PropertyCluesAndReports = "88";
  public static final String TheImplementationProcessOfThisBook = "89";
  public static final String auctionProceduresForRealizationOfProperty = "90";
  public static final String ProcessingExecutionDisputeBooks = "91";
  public static final String ExecutiveShallTransferProcedures = "92";
  public static final String Swivel = "93";
  public static final String NotificationClosed = "94";
}