package paul.wintz.music.chords.tests;

import static paul.wintz.music.chords.ChordQuality.*;
import static paul.wintz.music.intervals.IntervalEnum.*;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import static org.junit.Assert.assertEquals;

import paul.wintz.music.chords.*;
import paul.wintz.music.intervals.IntervalEnum;

public class IntervalToChordQualitiesMultimapTest {

	@Test
	public final void testThirdsMap() {
		Set<ChordQuality> actual = IntervalsToChordQualityMapper.makeSetOfChordQualitiesFromInterval(IntervalEnum.MAJOR_THIRD);
		Set<ChordQuality> expected = ImmutableSet.of(MAJOR_TRIAD, MAJOR_7, AUGMENTED_TRIAD, DOMINANT_7);
		assertEquals(expected, actual);
	}

	@Test
	public void testChordQualityFromIntervalSet() throws Exception {
		ChordQuality actual = IntervalsToChordQualityMapper.chordQualityFromIntervalSet(MAJOR_THIRD, PERFECT_FIFTH);
		ChordQuality expected = ChordQuality.MAJOR_TRIAD;
		assertEquals(expected, actual);
	}

	@Test
	public void testIdentifyOpenFifth() throws Exception {
		ChordQuality actual = IntervalsToChordQualityMapper.chordQualityFromIntervalSet(PERFECT_FIFTH);
		ChordQuality expected = ChordQuality.OPEN_FIFTH;
		assertEquals(expected, actual);
	}

	@Test
	public void testIdentifySus2() throws Exception {
		ChordQuality actual = IntervalsToChordQualityMapper.chordQualityFromIntervalSet(MAJOR_NINTH);
		ChordQuality expected = ChordQuality.SUS_2;
		assertEquals(expected, actual);
	}

	@Test
	public void testIdentifyBackToBack() throws Exception {
		ChordQuality actualMajor = IntervalsToChordQualityMapper.chordQualityFromIntervalSet(MAJOR_THIRD, PERFECT_FIFTH);
		ChordQuality expectedMajor = ChordQuality.MAJOR_TRIAD;
		assertEquals(expectedMajor, actualMajor);

		ChordQuality actualDiminished = IntervalsToChordQualityMapper.chordQualityFromIntervalSet(MINOR_THIRD, TRITONE);
		ChordQuality expectedDiminished = ChordQuality.DIMINISHED_TRIAD;
		assertEquals(expectedDiminished, actualDiminished);
	}

}
