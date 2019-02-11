package nlab.practice.jetpack.repository

import nlab.practice.jetpack.repository.model.History

/**
 * @author Doohyun
 */
class HistoryRepository {

    val items = listOf(
            History("Anko", false,
                    "기술의 사용은 가능하나, MVVM 사용 불가.\nPreview 가 제대로 동작안해 불편\nAndroid X 혼용 불명.."),


            History("MVVM with Dagger", true,
                    "AAC 사용안하는 MVVM 사용으로, 모든 로직을 ViewModel 과 xml 에서 처리 가능\nTDD 가능\n대신 Usecase 종류가 많아짐\nView 속성의 경계가 점점 애매모호.. ㅡㅡ^"),


            History("JetPack Navigation", false,
                    "아이디어는 좋았으나, Bottom Nav 에 적용하는 기존 메소드들의 커스텀이 힘듬(애니메이션, 규칙 등)\n프래그먼트가 무조건 파괴되고 재생성\n메모리 릭이 있음"),

            History("JetPack Paging Module", true,
                    "Countable, Unbound 상황에 대한 페이징 로직 제작\n실패, 새로고침에 대한 로직을 smooth 하게 되도록 구성해봄"),

            History("JetPack ListAdapter", true,
                    "submitList 만 적절하게 처리하면, 간단하고 쉽게 Adapter 의 트랜잭션을 처리할 수 있음.\n애니메이션도 좋아서 매우 쓸만함"),

            History("JetPack RecyclerView Selection", true,
                    "기본적인 Selection 처리에 대해 다루어 봤음\n이를 적용하기에 몇가지 제약이 있을 수 있음.\n  * 같은 키 사용 못함\n  * Select 된 항목이 하나도 없을 경우 Selection 자동해지\n  * 너무 추가해야할 코드가 많을 수 있음\n    (Selection 항목에 대한 패턴화가 필요할 것 같음)")
    )

}