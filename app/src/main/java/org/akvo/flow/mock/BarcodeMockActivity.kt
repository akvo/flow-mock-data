/*
 * Copyright (C) 2018 Stichting Akvo (Akvo Foundation)
 *
 * This file is part of Akvo Flow.
 *
 * Akvo Flow is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Akvo Flow is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Akvo Flow.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.akvo.flow.mock

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class BarcodeMockActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mock_caddisfly)
    }

    override fun onResume() {
        super.onResume()
        val resultIntent = Intent(intent)
//        resultIntent.putExtra("geoshapeResult", "  { \"type\": \"FeatureCollection\",\n" +
//                "    \"features\": [\n" +
//                "      { \"type\": \"Feature\",\n" +
//                "        \"geometry\": {\"type\": \"Point\", \"coordinates\": [102.0, 0.5]},\n" +
//                "        \"properties\": {\"prop0\": \"value0\"}\n" +
//                "        },\n" +
//                "      { \"type\": \"Feature\",\n" +
//                "        \"geometry\": {\n" +
//                "          \"type\": \"LineString\",\n" +
//                "          \"coordinates\": [\n" +
//                "            [102.0, 0.0], [103.0, 1.0], [104.0, 0.0], [105.0, 1.0]\n" +
//                "            ]\n" +
//                "          },\n" +
//                "        \"properties\": {\n" +
//                "          \"prop0\": \"value0\",\n" +
//                "          \"prop1\": 0.0\n" +
//                "          }\n" +
//                "        },\n" +
//                "      { \"type\": \"Feature\",\n" +
//                "         \"geometry\": {\n" +
//                "           \"type\": \"Polygon\",\n" +
//                "           \"coordinates\": [\n" +
//                "             [ [100.0, 0.0], [101.0, 0.0], [101.0, 1.0],\n" +
//                "               [100.0, 1.0], [100.0, 0.0] ]\n" +
//                "             ]\n" +
//                "         },\n" +
//                "         \"properties\": {\n" +
//                "           \"prop0\": \"value0\",\n" +
//                "           \"prop1\": {\"this\": \"that\"}\n" +
//                "           }\n" +
//                "         }\n" +
//                "       ]\n" +
//                "     }")
        resultIntent.putExtra("SCAN_RESULT", "123")
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}
