package paul.wintz.music.chords;

import java.util.*;

import com.google.common.collect.*;

import paul.wintz.music.intervals.IntervalEnum;

public class IntervalsToChordQualityMapper {
	private static final IntervalsToChordQualityMapper.IntervalToChordQualitiesMultimap map
	= new IntervalsToChordQualityMapper.IntervalToChordQualitiesMultimap();

	private static class IntervalToChordQualitiesMultimap {
		// Given a specific interval, we want to be able to get the set of chord qualities that
		// contain that interval.
		private final SetMultimap<IntervalEnum, ChordQuality> intervalsToChords;

		public IntervalToChordQualitiesMultimap() {

			int intervalCount = IntervalEnum.values().length;
			intervalsToChords = HashMultimap.create(intervalCount, 0);

			for(IntervalEnum interval : IntervalEnum.values()) {
				intervalsToChords.putAll(interval, IntervalsToChordQualityMapper.makeSetOfChordQualitiesFromInterval(interval));
			}
		}

		public Set<ChordQuality> chordQualitiesFromInterval(IntervalEnum interval){
			return intervalsToChords.get(interval);
		}

	}

	public static ChordQuality chordQualityFromIntervalSet(IntervalEnum... intervals) {
		Set<ChordQuality> set = EnumSet.allOf(ChordQuality.class);

		for(IntervalEnum interval : intervals) {
			Set<ChordQuality> toIntersect = map.chordQualitiesFromInterval(interval);
			set.retainAll(toIntersect);
		}

		if(set.isEmpty())
			return ChordQuality.INVALID;

		// Uses the first item based on the assumption that the enums are
		// sorted from simple to more complex.
		return set.iterator().next();
	}

	public static Set<ChordQuality> makeSetOfChordQualitiesFromInterval(IntervalEnum interval){
		final Set<ChordQuality> set = new HashSet<>();
		for(ChordQuality chordQuality : ChordQuality.values()) {
			if(chordQuality.getIntervals().contains(interval)) {
				set.add(chordQuality);
			}
		}
		return set;
	}

	public static Set<IntervalEnum> makeSetOfIntervalsToScaleDegree(int scaleDegree){
		final Set<IntervalEnum> set = new HashSet<>();
		for(IntervalEnum interval : IntervalEnum.values()) {
			if(interval.getScaleSteps() == scaleDegree) {
				set.add(interval);
			}
		}
		return set;
	}
}