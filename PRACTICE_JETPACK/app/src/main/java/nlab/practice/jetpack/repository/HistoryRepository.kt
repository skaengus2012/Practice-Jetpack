/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nlab.practice.jetpack.repository

import dagger.Reusable
import nlab.practice.jetpack.repository.model.History
import javax.inject.Inject

/**
 * @author Doohyun
 */
@Reusable
class HistoryRepository @Inject constructor() {

    val items = listOf(
        History(
            "Anko", false,
            "기술의 사용은 가능하나, MVVM 사용 불가.\nPreview 가 제대로 동작안해 불편\nAndroid X 혼용 불명.."
        ),

        History(
            "MVVM with Dagger", true,
            "AAC 사용안하는 MVVM 사용으로, 모든 로직을 ViewModel 과 xml 에서 처리 가능\nTDD 가능\n대신 Usecase 종류가 많아짐\nView 속성의 경계가 점점 애매모호.. ㅡㅡ^"
        ),

        History(
            "JetPack Navigation", false,
            "아이디어는 좋았으나, Bottom Nav 에 적용하는 기존 메소드들의 커스텀이 힘듬(애니메이션, 규칙 등)\n프래그먼트가 무조건 파괴되고 재생성\n메모리 릭이 있음"
        ),

        History(
            "JetPack Paging Module", true,
            "Countable, Unbound 상황에 대한 페이징 로직 제작\n실패, 새로고침에 대한 로직을 smooth 하게 되도록 구성해봄"
        ),

        History(
            "JetPack ListAdapter", true,
            "submitList 만 적절하게 처리하면, 간단하고 쉽게 Adapter 의 트랜잭션을 처리할 수 있음.\n애니메이션도 좋아서 매우 쓸만함"
        ),

        History(
            "JetPack RecyclerView Selection", true,
            "기본적인 Selection 처리에 대해 다루어 봤음\n이를 적용하기에 몇가지 제약이 있을 수 있음.\n  * 같은 키 사용 못함\n  * Select 된 항목이 하나도 없을 경우 Selection 자동해지\n  * 너무 추가해야할 코드가 많을 수 있음\n    (Selection 항목에 대한 패턴화가 필요할 것 같음)"
        ),

        History(
            "RecyclerView ItemTouchHelper", true,
            "Drag & Drop 과 Swipe 는 ItemTouchHelper 의 구현만으로 쉽게 할 수 있음.\n  * DI 로 연결해야하는 이슈가 있었음\n  *  Multi-binding <-> Named Tag 주목"
        ),

        History(
            "CollapsingToolbar Layout 연습", true,
            "CoordinatorLayout, AppBarLayout 과 함께, Collapsing 기능을 좀 더 자세히 구현할 수 있음\n  * 기본적인 예제 구현사항임\n  *  CoordinatorLayout anchor 연구는 해봐야함"
        ),

        History(
            "SlidingUpPanelLayout 연습", true,
            "뮤직 플레이어에서 주로 사용하는, 오픈소스에 대한 연구\n  * 모든 부분을 MVVM 으로 구현해봄\n  *  interface - Delegate 를 시도한 두번째 예제\n  *  개인앱 제작 시, 조금더 연구는 필요"
        )
    )

}