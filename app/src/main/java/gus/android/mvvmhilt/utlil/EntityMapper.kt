package gus.android.mvvmhilt.utlil

// model/Blog is the DomainModel
// Entity is the BlogNetworkEntity
interface EntityMapper<Entity, DomainModel> {

    fun mapFromEntity(entity: Entity): DomainModel

    fun mapToEntity(domainModel: DomainModel): Entity
}