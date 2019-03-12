/*
 *
 *  * Copyright (C) 2016 Kyligence Inc. All rights reserved.
 *  *
 *  * http://kyligence.io
 *  *
 *  * This software is the confidential and proprietary information of
 *  * Kyligence Inc. ("Confidential Information"). You shall not disclose
 *  * such Confidential Information and shall use it only in accordance
 *  * with the terms of the license agreement you entered into with
 *  * Kyligence Inc.
 *  *
 *  * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *  * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 *  * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 *  * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 *  * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 *  * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 *  * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 *  * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package mondrian.rolap;

import java.util.ArrayList;
import java.util.List;

public class ExcludeDim {

    private String dimension;

    private boolean excludeAll;

    private List<String> excludeCols = new ArrayList<>();

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public boolean isExcludeAll() {
        return excludeAll;
    }

    public void setExcludeAll(boolean excludeAll) {
        this.excludeAll = excludeAll;
    }

    public List<String> getExcludeCols() {
        return excludeCols;
    }

    public void setExcludeCols(List<String> excludeCols) {
        this.excludeCols = excludeCols;
    }

    public void addExcludeCols(String colName) {
        this.excludeCols.add(colName);
    }
}