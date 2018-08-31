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
package mondrian.util;

import java.util.LinkedList;
import java.util.List;

import mondrian.calc.TupleList;
import mondrian.calc.impl.ListTupleList;
import mondrian.olap.Axis;
import mondrian.olap.Member;
import mondrian.rolap.RolapAxis;

public class AxesSortUtil {

    public void sortAxes(Axis[] axes) {
        if (axes == null) {
            return;
        }
        for (int i = 0; i < axes.length; i++) {
            if (axes[i] instanceof RolapAxis) {
                RolapAxis axis = sortRolapAxis((RolapAxis) axes[i]);
                axes[i] = axis;
            }
        }
    }

    private RolapAxis sortRolapAxis(RolapAxis axis) {
        int prevChangedPosition = -1;
        TupleList tupleList = getTupleListCopy(axis.getTupleList());
        for (int i = 0; i < tupleList.size(); i++) {
            List<Member> tuple = tupleList.get(i);
            int allMemIdxOfTuple = -1;
            if ((allMemIdxOfTuple = allMemIdxOfTuple(tuple)) != -1) {
                int changedPosition = changeTuplePosition(tuple, tupleList, i, prevChangedPosition, allMemIdxOfTuple);
                if (changedPosition == -2) {
                    tupleList.remove(i);
                    i--;
                } else {
                    prevChangedPosition = changedPosition;
                }
            }
        }
        return new RolapAxis(tupleList);
    }

    private TupleList getTupleListCopy(TupleList tupleList) {
        List<Member> members = new LinkedList<>();
        int arity = tupleList.get(0).size();
        for (List<Member> tuple : tupleList) {
            members.addAll(tuple);
        }
        return new ListTupleList(arity, members);
    }

    private int allMemIdxOfTuple(List<Member> tuple) {
        if (tuple != null && tuple.size() > 0) {
            for (int i = 0; i < tuple.size(); i++) {
                if (isAllMember(tuple.get(i))) {
                    return i;
                }
            }
        }
        return -1;
    }

    private boolean isAllMember(Member member) {
        return member.getLevel().toString().contains("[(All)]");
    }

    private int changeTuplePosition(List<Member> tuple, TupleList tupleList, int currentTuplePosition,
            int prevChangedPosition, int allMemIdxOfTuple) {
        for (int i = prevChangedPosition + 1; i < tupleList.size(); i++) {
            if (i == currentTuplePosition) {
                // 如果没找到要插入的位置，表示此元素多余，返回-2
                return -2;
            }
            if (shouldInsertBefore(tuple, tupleList.get(i), allMemIdxOfTuple)) {
                tupleList.remove(currentTuplePosition);
                tupleList.add(i, tuple);
                return i;
            }
        }
        return prevChangedPosition;
    }

    /*
    *  (1, 2, All)  (1, 3, 1)    false
    *  (1, All, All)  (1, 3, 1)  true, but (1, 1 ,All) will insert before this
    *  (1, All, All) (1, 1, 1) ==> (1, 1, All)  (1, 1, 1)
    * */
    private boolean shouldInsertBefore(List<Member> tuple0, List<Member> tuple1, int allMemIdxOfTuple) {
        for (int i = 0; i < allMemIdxOfTuple; i++) {
            if (!tuple0.get(i).getUniqueName().equals(tuple1.get(i).getUniqueName())) {
                return false;
            }
        }

        return true;
    }

}
