/*
 * Copyright 2020 Nikita Shakarun
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vodafone.v10.sound;

public class SoundPlayer {
	private SoundTrack[] tracks = new SoundTrack[16];

	public static SoundPlayer getPlayer() {
		return new SoundPlayer();
	}

	public SoundTrack getTrack() {
		for (int i = 0; i < 16; i++) {
			if (tracks[i] == null) {
				tracks[i] = new SoundTrack();
				return tracks[i];
			}
		}
		throw new IllegalStateException("no more tracks available!");
	}
}