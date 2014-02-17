package ie.ucc.bis.supportinglife.domain;

import ie.ucc.bis.supportinglife.helper.DateHandlerUtils;
import ie.ucc.bis.supportinglife.rule.engine.Diagnostic;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author timothyosullivan
 */

public class PatientAssessment implements Serializable {
	
	/**
	 * Generated Serial ID
	 */
	private static final long serialVersionUID = 7692081022011255176L;
	
	private int id;
	
	private String hsaUserId;
	private String nationalId;
	private String nationalHealthId;
	private String childFirstName;
	private String childSurname;
	private Date birthDate;
	private String gender;
	private String caregiverName;
	private String relationship;
	private String physicalAddress;
	private String villageTa;
	private Date visitDate;	
	
	// Look Symptoms
	private boolean chestIndrawing;
	private Integer breathsPerMinute;
	private boolean sleepyUnconscious;	
	private boolean palmarPallor;	
	private String muacTapeColour;	
	private boolean swellingBothFeet;	
	
	// Ask Look Symptoms
	private String problem;
	private boolean cough;	
	private Integer coughDuration;
	private boolean diarrhoea;
	private Integer diarrhoeaDuration;
	private boolean bloodInStool;
	private boolean fever;
	private Integer feverDuration;
	private boolean convulsions;
	private boolean difficultyDrinkingOrFeeding;
	private boolean unableToDrinkOrFeed;
	private boolean vomiting;
	private boolean vomitsEverything;
	private boolean redEye;
	private Integer redEyeDuration;
	private boolean difficultySeeing;
	private Integer difficultySeeingDuration;
	private boolean cannotTreatProblem;
	private String cannotTreatProblemDetails;
	
	// Classification and Treatment Diagnostics
	private List<Diagnostic> diagnostics;
	
	public PatientAssessment() {
		setDiagnostics(new ArrayList<Diagnostic>());
	}
	
	public PatientAssessment(Integer id, String hsaUserId, String nationalId, String nationalHealthId, 
					String childFirstName, String childSurname, String birthDate,
					String gender, String caregiverName, String relationship, String physicalAddress,
					String villageTa, String visitDate, String chestIndrawing, Integer breathsPerMinute,
					String sleepyUnconscious, String palmarPallor, String muacTapeColour, 
					String swellingBothFeet, String problem, String cough, Integer coughDuration,
					String diarrhoea, Integer diarrhoeaDuration, String bloodInStool, String fever,
					Integer feverDuration, String convulsions, String difficultyDrinkingOrFeeding,
					String unableToDrinkOrFeed, String vomiting, String vomitsEverything,
					String redEye, Integer redEyeDuration, String difficultySeeing,
					Integer difficultySeeingDuration, String cannotTreatProblem, 
					String cannotTreatProblemDetails) {
		
		setId(id);
		setHsaUserId(hsaUserId);
		setNationalId(nationalId);
		setNationalHealthId(nationalHealthId);
		setChildFirstName(childFirstName);
		setChildSurname(childSurname);
		setGender(gender);
		setCaregiverName(caregiverName);
		setRelationship(relationship);
		setPhysicalAddress(physicalAddress);
		setVillageTa(villageTa);
		setChestIndrawing(Boolean.valueOf(chestIndrawing));
		setBreathsPerMinute(breathsPerMinute);
		setSleepyUnconscious(Boolean.valueOf(sleepyUnconscious));
		setPalmarPallor(Boolean.valueOf(palmarPallor));
		setMuacTapeColour(muacTapeColour);
		setDiarrhoea(Boolean.valueOf(diarrhoea));
		setDiarrhoeaDuration(diarrhoeaDuration);
		setBloodInStool(Boolean.valueOf(bloodInStool));
		setFever(Boolean.valueOf(fever));
		setFeverDuration(feverDuration);
		setConvulsions(Boolean.valueOf(convulsions));
		setDifficultyDrinkingOrFeeding(Boolean.valueOf(difficultyDrinkingOrFeeding));
		setUnableToDrinkOrFeed(Boolean.valueOf(unableToDrinkOrFeed));
		setVomiting(Boolean.valueOf(vomiting));
		setVomitsEverything(Boolean.valueOf(vomitsEverything));
		setRedEye(Boolean.valueOf(redEye));
		setRedEyeDuration(redEyeDuration);
		setDifficultySeeing(Boolean.valueOf(difficultySeeing));
		setDifficultySeeingDuration(difficultySeeingDuration);
		setCannotTreatProblem(Boolean.valueOf(cannotTreatProblem));
		setCannotTreatProblemDetails(cannotTreatProblemDetails);
				
		try {
			setBirthDate(DateHandlerUtils.parseDate(birthDate));
			setVisitDate(DateHandlerUtils.parseDate(visitDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		setDiagnostics(new ArrayList<Diagnostic>());
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHsaUserId() {
		return hsaUserId;
	}

	public void setHsaUserId(String hsaUserId) {
		this.hsaUserId = hsaUserId;
	}

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public String getNationalHealthId() {
		return nationalHealthId;
	}

	public void setNationalHealthId(String nationalHealthId) {
		this.nationalHealthId = nationalHealthId;
	}

	public String getChildFirstName() {
		return childFirstName;
	}

	public void setChildFirstName(String childFirstName) {
		this.childFirstName = childFirstName;
	}

	public String getChildSurname() {
		return childSurname;
	}

	public void setChildSurname(String childSurname) {
		this.childSurname = childSurname;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getCaregiverName() {
		return caregiverName;
	}

	public void setCaregiverName(String caregiverName) {
		this.caregiverName = caregiverName;
	}

	public String getRelationship() {
		return relationship;
	}

	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}

	public String getPhysicalAddress() {
		return physicalAddress;
	}

	public void setPhysicalAddress(String physicalAddress) {
		this.physicalAddress = physicalAddress;
	}

	public String getVillageTa() {
		return villageTa;
	}

	public void setVillageTa(String villageTa) {
		this.villageTa = villageTa;
	}

	public Date getVisitDate() {
		return visitDate;
	}

	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}

	public boolean isChestIndrawing() {
		return chestIndrawing;
	}

	public void setChestIndrawing(boolean chestIndrawing) {
		this.chestIndrawing = chestIndrawing;
	}

	public Integer getBreathsPerMinute() {
		return breathsPerMinute;
	}

	public void setBreathsPerMinute(Integer breathsPerMinute) {
		this.breathsPerMinute = breathsPerMinute;
	}

	public boolean isSleepyUnconscious() {
		return sleepyUnconscious;
	}

	public void setSleepyUnconscious(boolean sleepyUnconscious) {
		this.sleepyUnconscious = sleepyUnconscious;
	}

	public boolean isPalmarPallor() {
		return palmarPallor;
	}

	public void setPalmarPallor(boolean palmarPallor) {
		this.palmarPallor = palmarPallor;
	}

	public String getMuacTapeColour() {
		return muacTapeColour;
	}

	public void setMuacTapeColour(String muacTapeColour) {
		this.muacTapeColour = muacTapeColour;
	}

	public boolean isSwellingBothFeet() {
		return swellingBothFeet;
	}

	public void setSwellingBothFeet(boolean swellingBothFeet) {
		this.swellingBothFeet = swellingBothFeet;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public boolean isCough() {
		return cough;
	}

	public void setCough(boolean cough) {
		this.cough = cough;
	}

	public Integer getCoughDuration() {
		return coughDuration;
	}

	public void setCoughDuration(Integer coughDuration) {
		this.coughDuration = coughDuration;
	}

	public boolean isDiarrhoea() {
		return diarrhoea;
	}

	public void setDiarrhoea(boolean diarrhoea) {
		this.diarrhoea = diarrhoea;
	}

	public Integer getDiarrhoeaDuration() {
		return diarrhoeaDuration;
	}

	public void setDiarrhoeaDuration(Integer diarrhoeaDuration) {
		this.diarrhoeaDuration = diarrhoeaDuration;
	}

	public boolean isBloodInStool() {
		return bloodInStool;
	}

	public void setBloodInStool(boolean bloodInStool) {
		this.bloodInStool = bloodInStool;
	}

	public boolean isFever() {
		return fever;
	}

	public void setFever(boolean fever) {
		this.fever = fever;
	}

	public Integer getFeverDuration() {
		return feverDuration;
	}

	public void setFeverDuration(Integer feverDuration) {
		this.feverDuration = feverDuration;
	}

	public boolean isConvulsions() {
		return convulsions;
	}

	public void setConvulsions(boolean convulsions) {
		this.convulsions = convulsions;
	}

	public boolean isDifficultyDrinkingOrFeeding() {
		return difficultyDrinkingOrFeeding;
	}

	public void setDifficultyDrinkingOrFeeding(boolean difficultyDrinkingOrFeeding) {
		this.difficultyDrinkingOrFeeding = difficultyDrinkingOrFeeding;
	}

	public boolean isUnableToDrinkOrFeed() {
		return unableToDrinkOrFeed;
	}

	public void setUnableToDrinkOrFeed(boolean unableToDrinkOrFeed) {
		this.unableToDrinkOrFeed = unableToDrinkOrFeed;
	}

	public boolean isVomiting() {
		return vomiting;
	}

	public void setVomiting(boolean vomiting) {
		this.vomiting = vomiting;
	}

	public boolean isVomitsEverything() {
		return vomitsEverything;
	}

	public void setVomitsEverything(boolean vomitsEverything) {
		this.vomitsEverything = vomitsEverything;
	}

	public boolean isRedEye() {
		return redEye;
	}

	public void setRedEye(boolean redEye) {
		this.redEye = redEye;
	}

	public Integer getRedEyeDuration() {
		return redEyeDuration;
	}

	public void setRedEyeDuration(Integer redEyeDuration) {
		this.redEyeDuration = redEyeDuration;
	}

	public boolean isDifficultySeeing() {
		return difficultySeeing;
	}

	public void setDifficultySeeing(boolean difficultySeeing) {
		this.difficultySeeing = difficultySeeing;
	}

	public Integer getDifficultySeeingDuration() {
		return difficultySeeingDuration;
	}

	public void setDifficultySeeingDuration(Integer difficultySeeingDuration) {
		this.difficultySeeingDuration = difficultySeeingDuration;
	}

	public boolean isCannotTreatProblem() {
		return cannotTreatProblem;
	}

	public void setCannotTreatProblem(boolean cannotTreatProblem) {
		this.cannotTreatProblem = cannotTreatProblem;
	}

	public String getCannotTreatProblemDetails() {
		return cannotTreatProblemDetails;
	}

	public void setCannotTreatProblemDetails(String cannotTreatProblemDetails) {
		this.cannotTreatProblemDetails = cannotTreatProblemDetails;
	}

	public List<Diagnostic> getDiagnostics() {
		return diagnostics;
	}

	public void setDiagnostics(List<Diagnostic> diagnostics) {
		this.diagnostics = diagnostics;
	}	
}
