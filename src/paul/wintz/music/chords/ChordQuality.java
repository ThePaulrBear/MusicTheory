package paul.wintz.music.chords;

import paul.wintz.music.UnicodSymbol;
import paul.wintz.music.intervals.IntervalEnum;

public enum ChordQuality {

	//	OPEN_FIFTH (IntervalQuality.NONE, IntervalQuality.PERFECT, "~"),
	//	SUS_2 (IntervalQuality.NONE, IntervalQuality.PERFECT, null, null, IntervalQuality.MAJOR, null, " sus2"),
	//	SUS_4 (IntervalQuality.NONE, IntervalQuality.PERFECT, null, null, null, IntervalQuality.PERFECT, " sus4"),

	MINOR_TRIAD 		(" min", IntervalEnum.MINOR_THIRD, IntervalEnum.PERFECT_FIFTH),
	DIMINISHED_TRIAD 	(UnicodSymbol.DIMINISHED, IntervalEnum.MINOR_THIRD, IntervalEnum.TRITONE),
	MINOR_SEVENTH 		(" min7", IntervalEnum.MINOR_THIRD, IntervalEnum.PERFECT_FIFTH, IntervalEnum.MINOR_SEVENTH),

	MAJOR_TRIAD 		(" maj", IntervalEnum.MAJOR_THIRD, IntervalEnum.PERFECT_FIFTH),
	AUGMENTED_TRIAD 	("+", IntervalEnum.MAJOR_THIRD, IntervalEnum.AUGMENTED_FIFTH),

	MAJOR_SEVENTH 		(" maj7", IntervalEnum.MAJOR_THIRD, IntervalEnum.PERFECT_FIFTH, IntervalEnum.MAJOR_SEVENTH),
	DOMINANT_SEVENTH 	("7", IntervalEnum.MAJOR_THIRD, IntervalEnum.PERFECT_FIFTH, IntervalEnum.MINOR_SEVENTH),
	DIMINISHED_SEVENTH 	(UnicodSymbol.DIMINISHED + "7", IntervalEnum.MINOR_THIRD, IntervalEnum.TRITONE, IntervalEnum.DIMINISHED_SEVENTH),
	HALF_DIMINISHED_SEVENTH (UnicodSymbol.HALF_DIMINISHED + "7", IntervalEnum.MINOR_THIRD, IntervalEnum.TRITONE, IntervalEnum.MINOR_SEVENTH),
	INVALID ("?");

	private final IntervalEnum[] intervalEnums;
	//	final private IntervalEnum third;
	//	final private IntervalEnum fifth;
	//	final private IntervalEnum seventh;
	//	final private IntervalEnum ninth;
	//	final private IntervalEnum eleventh;
	//	final private IntervalEnum thirteenth;
	private final String sufix;

	//Seventh chords
	private ChordQuality(String sufix, IntervalEnum... intervals){
		this.sufix = sufix;
		this.intervalEnums = intervals;
	}



	//Triads


	//	//Seventh chords
	//	private ChordQuality(String sufix, IntervalQuality third, IntervalQuality fifth){
	//		this(sufix, third, fifth, IntervalQuality.NONE, IntervalQuality.NONE, IntervalQuality.NONE, IntervalQuality.NONE);
	//	}
	//
	//	//Seventh chords
	//	private ChordQuality(IntervalQuality third, IntervalQuality fifth, IntervalQuality seventh, String sufix){
	//		this(sufix, third, fifth, seventh);
	//	}
	//
	//	//Seventh chords
	//	private ChordQuality(String sufix, IntervalQuality third, IntervalQuality fifth, IntervalQuality seventh){
	//		this(sufix, third, fifth, seventh, IntervalQuality.NONE, IntervalQuality.NONE, IntervalQuality.NONE);
	//	}

	//	//Thirteenth chords
	//	private ChordQuality(
	//			IntervalQuality third,
	//			IntervalQuality fifth,
	//			IntervalQuality seventh,
	//			IntervalQuality ninth,
	//			IntervalQuality eleventh,
	//			IntervalQuality thirteenth,
	//			String sufix){
	//				this(sufix, third, fifth, seventh, ninth, eleventh, thirteenth);
	//			}
	//
	//	//Thirteenth chords
	//	private ChordQuality(
	//			String sufix,
	//			IntervalQuality third,
	//			IntervalQuality fifth,
	//			IntervalQuality seventh,
	//			IntervalQuality ninth,
	//			IntervalQuality eleventh,
	//			IntervalQuality thirteenth){
	//		this.third = third;
	//		this.fifth = fifth;
	//		this.seventh = seventh;
	//		this.ninth = ninth;
	//		this.eleventh = eleventh;
	//		this.thirteenth = thirteenth;
	//		this.sufix = sufix;
	//	}

	//	//Invalid constructor
	//	private ChordQuality() {
	//		this("invalid", IntervalQuality.NONE, IntervalQuality.NONE, IntervalQuality.NONE, IntervalQuality.NONE, IntervalQuality.NONE, IntervalQuality.NONE);
	//	}

	public IntervalEnum[] getIntervals(){
		if(this == INVALID)
			return null;
		else
			return intervalEnums;
	}

	//	@Deprecated
	//	public ArrayList<IntervalEnum> getIntervalsOld(){
	//		if(this == INVALID) return null;
	//
	//		ArrayList<IntervalEnum> intervalEnums = new ArrayList<>();
	//		intervalEnums.add(IntervalEnum.UNISON);
	//
	//		switch (getThird()) {
	//		case MAJOR_THIRD:
	//			intervalEnums.add(IntervalEnum.MAJOR_THIRD);
	//			break;
	//		case MINOR_THIRD:
	//			intervalEnums.add(IntervalEnum.MINOR_THIRD);
	//			break;
	//		}
	//		switch (getFifth()) {
	//		case PERFECT_FIFTH:
	//			intervalEnums.add(IntervalEnum.PERFECT_FIFTH);
	//			break;
	//		case TRITONE:
	//			intervalEnums.add(IntervalEnum.TRITONE);
	//			break;
	//		case AUGMENTED_FIFTH:
	//			intervalEnums.add(IntervalEnum.MINOR_SIXTH);
	//			break;
	//		default:
	//			System.err.println("Error in ChordQuality.getIntervals(). fifth: " + fifth.name() + " was not handled well");
	//		}
	//		switch (getSeventh()) {
	//		case MAJOR_SEVENTH:
	//			intervalEnums.add(IntervalEnum.MAJOR_SEVENTH);
	//			break;
	//		case MINOR_SEVENTH:
	//			intervalEnums.add(IntervalEnum.MINOR_SEVENTH);
	//			break;
	//		case DIMINISHED_SEVENTH:
	//			intervalEnums.add(IntervalEnum.MAJOR_SIXTH);
	//			break;
	//		case NONE:
	//			break;
	//		default:
	//			System.err.println("Error in ChordQuality.getIntervals(). seventh: " + seventh.name() + " was not handled well");
	//		}
	//
	//		return intervalEnums;
	//	}

	//	public static ChordQuality  calculate(
	//			IntervalQuality thirdType,
	//			IntervalQuality fifthType
	//			){
	//		return calculate(thirdType, fifthType, IntervalQuality.NONE);
	//	}
	//
	//	public static ChordQuality  calculate(
	//			IntervalQuality thirdType,
	//			IntervalQuality fifthType,
	//			IntervalQuality seventhType
	//			){
	//		return calculate(thirdType, fifthType, seventhType, IntervalQuality.NONE);
	//	}
	//
	//	public static ChordQuality  calculate(
	//			IntervalQuality thirdType,
	//			IntervalQuality fifthType,
	//			IntervalQuality seventhType,
	//			IntervalQuality ninthType
	//			){
	//		return calculate(thirdType, fifthType, seventhType, ninthType, IntervalQuality.NONE);
	//	}
	//
	//	public static ChordQuality  calculate(
	//			IntervalQuality thirdType,
	//			IntervalQuality fifthType,
	//			IntervalQuality seventhType,
	//			IntervalQuality ninthType,
	//			IntervalQuality eleventhType
	//			){
	//		return calculate(thirdType, fifthType, seventhType, ninthType, eleventhType, IntervalQuality.NONE);
	//	}

	@SuppressWarnings("incomplete-switch")
	public static  ChordQuality calculate(
			IntervalEnum thirdType,
			IntervalEnum fifthType,
			IntervalEnum seventhType,
			IntervalEnum ninthType,
			IntervalEnum eleventhType,
			IntervalEnum thirteenthType
			){
		//		if(thirdType == IntervalEnum.MULTIPLE
		//				|| fifthType == IntervalEnum.MULTIPLE
		//				|| seventhType == IntervalEnum.MULTIPLE) return INVALID;

		switch (thirdType) {
		case MINOR_THIRD:
			switch(fifthType){
			//			case NULL: //If there is no fifth, assume it is perfect.
			case PERFECT_FIFTH:
				switch(seventhType){
				case NULL:
					return ChordQuality.MINOR_TRIAD;
				case MINOR_SEVENTH:
					return ChordQuality.MINOR_SEVENTH;
					//				case MAJOR_SEVENTH:
					//					return ChordQuality.MINOR_MAJOR_SEVENTH;
				}
				break;
			case TRITONE:
				switch(seventhType){
				case NULL:
					if(thirteenthType == IntervalEnum.MAJOR_THIRTEENTH)
						return ChordQuality.DIMINISHED_SEVENTH;
					return ChordQuality.DIMINISHED_TRIAD;
				case MINOR_SEVENTH:
					return ChordQuality.HALF_DIMINISHED_SEVENTH;
				case DIMINISHED_SEVENTH:
					return ChordQuality.DIMINISHED_SEVENTH;
				}
				break;
			}
			break;

		case MAJOR_THIRD:
			switch(fifthType){
			case NULL: //If there is no fifth, assume it is perfect.
				if(thirteenthType == IntervalEnum.MINOR_THIRTEENTH)
					return ChordQuality.AUGMENTED_TRIAD;
				break;
			case PERFECT_FIFTH:
				switch(seventhType){
				case NULL:
					return ChordQuality.MAJOR_TRIAD;
				case MAJOR_SEVENTH:
					return ChordQuality.MAJOR_SEVENTH;
				case MINOR_SEVENTH:
					return ChordQuality.DOMINANT_SEVENTH;
				}
				break;
			case AUGMENTED_FIFTH:
				return ChordQuality.AUGMENTED_TRIAD;
			}
			break;
		case NULL:
			switch(fifthType){
			case PERFECT_FIFTH:
				//				if(ninthType == IntervalEnum.MAJOR_THIRD){
				//					return SUS_2;
				//				} else if(eleventhType == IntervalQuality.PERFECT){
				//					return SUS_4;
				//				}
			}
			break;
		}
		return ChordQuality.INVALID;

	}

	@SuppressWarnings("incomplete-switch")
	/*public static ChordQuality calculate(IntervalEnum... stackedThirds){
		for(IntervalEnum interval : stackedThirds){
			if(interval == null || interval == IntervalEnum.MULTIPLE){
				return ChordQuality.INVALID;
			}
		}

		IntervalEnum third = stackedThirds[0];
		IntervalEnum fifth = stackedThirds[1];
		IntervalEnum seventh = stackedThirds[2];
		IntervalEnum ninth = stackedThirds[3];
		IntervalEnum eleventh = stackedThirds[4];
		IntervalEnum thirteenth = stackedThirds[5];

		switch (third) {
		case MINOR_THIRD:
			switch(fifth){
			case null: //If there is no fifth, assume it is perfect.
			case PERFECT:
				switch(seventh){
				case MAJOR:
				case NONE:
					return ChordQuality.MINOR_TRIAD;
				case MINOR:
					return ChordQuality.MINOR_SEVENTH;
				}
				break;
			case DIMINISHED:
				switch(seventh){
				case NONE:
					return ChordQuality.DIMINISHED_TRIAD;
				case MINOR:
					return ChordQuality.HALF_DIMINISHED_SEVENTH;
				case DIMINISHED:
					return ChordQuality.DIMINISHED_SEVENTH;
				}
				break;
			}
			break;

		case MAJOR_THIRD:
			switch(fifth){
			case NONE: //If there is no fifth, assume it is perfect.
			case PERFECT:
				switch(seventh){
				case NONE:
					return ChordQuality.MAJOR_TRIAD;
				case MAJOR:
					return ChordQuality.MAJOR_SEVENTH;
				case MINOR:
					return ChordQuality.DOMINANT_SEVENTH;
				}
				break;
			case AUGMENTED:
				switch(seventh){
				case NONE:
					return ChordQuality.AUGMENTED_TRIAD;
				}
				break;
			}
			break;
		case null:
			switch(fifth){
			case PERFECT_FIFTH:
//				if(ninthType == IntervalEnum.MAJOR_THIRD){
//					return SUS_2;
//				} else if(eleventhType == IntervalQuality.PERFECT){
//					return SUS_4;
//				}
			}
			break;
		}
		return ChordQuality.INVALID;
	}
	 */
	boolean isTriad(){
		return (!isSeventhChord() && this != INVALID);
	}

	public boolean isSeventhChord(){
		return (getSeventh() != null && this != INVALID);
	}

	public boolean isValid(){
		return(this != INVALID);
	}

	//	public ChordQuality getAsTriad() {
	//		if(this.isTriad()) return this;
	//		else{
	//			return calculate(getThird(), getFifth());
	//		}
	//	}

	public String getSufix() {
		return sufix;
	}

	public IntervalEnum getThird() {
		final int index = 0;

		return getStackedThird(index);
	}

	public IntervalEnum getFifth() {
		final int index = 1;

		return getStackedThird(index);
	}

	public IntervalEnum getSeventh() {
		final int index = 2;

		return getStackedThird(index);
	}

	public IntervalEnum getNinth() {
		final int index = 3;

		return getStackedThird(index);
	}

	public IntervalEnum getEleventh() {
		final int index = 4;

		return getStackedThird(index);
	}

	public IntervalEnum getThirteenth() {
		final int index = 5;

		return getStackedThird(index);
	}



	/**
	 * @param index
	 * @return
	 */
	private IntervalEnum getStackedThird(int index) {
		if(intervalEnums == null || intervalEnums.length < index) return null;
		return intervalEnums[index];
	}



}
